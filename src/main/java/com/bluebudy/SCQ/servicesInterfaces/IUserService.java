/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.servicesInterfaces;

import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.domain.VerificationToken;
import com.bluebudy.SCQ.forms.UserFormulario;
import java.util.Optional;

/**
 *
 * @author Alexandre
 */
public interface IUserService {
    Optional<User> registerNewUserAccount(UserFormulario userDto); 
    public void createVerificationToken(User user, String token);
    public Optional<VerificationToken> getVerificationToken(String token);
    public void grantAdmin(Long userId);
    public void saveRegisteredUser(User user);
  
}
