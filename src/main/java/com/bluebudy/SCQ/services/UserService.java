/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.services;

import com.bluebudy.SCQ.repository.UserRepository;
import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.domain.VerificationToken;
import com.bluebudy.SCQ.forms.UserFormulario;
import com.bluebudy.SCQ.repository.VerificationTokenRepository;
import com.bluebudy.SCQ.servicesInterfaces.IUserService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alexandre
 */
@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private VerificationTokenRepository tokenRepo;
    
    @Autowired
    private PasswordEncoder encoder;
    
    
    @Transactional 
    @Override
    public Optional<User> registerNewUserAccount(@Valid UserFormulario userForm)  {
        if(emailExists(userForm.getMail())){
            return Optional.empty();
        } else {
            return Optional.of(repository.save(userForm.generateUser(encoder)));
        }
             
    }
 
    private boolean emailExists(String email) {
        return repository.findByMail(email) != null;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(120));
        tokenRepo.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> getVerificationToken(String token) {
        Optional<VerificationToken> veriOptional = tokenRepo.findByToken(token);
        return veriOptional;
    }

    @Override
    public void saveRegisteredUser(User user) {
            repository.findById(user.getId()).map(usuario -> {
            usuario.setEnabled(true);
            return repository.save(usuario);
        }).get();
    }

    @Override
    public void grantAdmin(Long userId) {
        repository.findById(userId).map( user -> {
            user.setAdmin(true);
            repository.save(user);
            return user;
        });
    }
}
