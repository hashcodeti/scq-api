/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.dtos.AuthenticationDTO;
import com.bluebudy.SCQ.forms.LoginForm;
import com.bluebudy.SCQ.repository.UserRepository;
import com.bluebudy.SCQ.services.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Alexandre
 */
@RestController
public class AuthenticationController {
    
    @Autowired
    private UserRepository repo;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form){
        UsernamePasswordAuthenticationToken loginData = form.converter();
        User usuario = repo.findByMail(form.getUsuario());
        try {
              Authentication authentication = authenticationManager.authenticate(loginData);
              User user = (User) authentication.getPrincipal();
              String token = tokenService.gerarToken(user.getUsername());
              String role = user.isAdmin()?"ADMIN_ROLE":"USER_ROLE";
              return ResponseEntity.ok(new AuthenticationDTO(token,"Bearer",role,user.getName()));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new AuthenticationDTO(null,"Bearer",null,usuario.getName()));
        }
        
      
    }
    
    
    @RequestMapping(path = "/authCheck", method = RequestMethod.GET)
    public RedirectView confirmRegistration(HttpServletRequest request , HttpServletResponse httpServletResponse, RedirectAttributes attributes) {
          
        httpServletResponse.setStatus(302);
        return new RedirectView("https://cationscq.com/Login");
     
    }
   
    
}
