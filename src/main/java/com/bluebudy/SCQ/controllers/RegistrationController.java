/*
	 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.controllers;

import java.util.Calendar;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.domain.VerificationToken;
import com.bluebudy.SCQ.events.OnRegistrationCompleteEvent;
import com.bluebudy.SCQ.events.OnUserEnable;
import com.bluebudy.SCQ.forms.UserFormulario;
import com.bluebudy.SCQ.services.UserService;
import com.bluebudy.SCQ.servicesInterfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Alexandre
 */
@RestController
public class RegistrationController {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    UserService userService;

    @Autowired
    private IUserService service;
    
  
    
    @PostMapping("/user/registration")
    @ResponseBody
    public ResponseEntity<?> registerUserAccount(@RequestBody @Valid UserFormulario form,HttpServletRequest request) {

        Optional<User> user = userService.registerNewUserAccount(form);
        if (user.isEmpty()) {
            return ResponseEntity.ok("message");
        } else {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user.get(),request.getLocale(), appUrl));
            return ResponseEntity.ok("redirect");
        }
    }

        @RequestMapping(path = "/regitrationConfirm/{token}", method = RequestMethod.GET)
        public RedirectView confirmRegistration(@PathVariable("token") String token, HttpServletRequest request , HttpServletResponse httpServletResponse, RedirectAttributes attributes) {
              
               httpServletResponse.setStatus(302);
     

            Optional<VerificationToken> verificationToken = service.getVerificationToken(token);
            if (!verificationToken.isPresent()) {
                return new RedirectView("/usuarioInvalido");
                
                 
              }

            User user = verificationToken.get().getUser();
            Calendar cal = Calendar.getInstance();
            if ((verificationToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
               return new RedirectView("/TokenExpirado");
              
            }

            user.setEnabled(true);
         
            service.saveRegisteredUser(user);
            eventPublisher.publishEvent(new OnUserEnable(user, request.getContextPath()));
           
            return new RedirectView("https://cationscq.com/Home");
         
        }
        
        @GetMapping("/user/adminRoleGrant/{userId}")
        @ResponseBody
        public ResponseEntity<?> userAdminGrant(@PathVariable("userId") Long userId, HttpServletRequest request) {
                service.grantAdmin(userId);
                return ResponseEntity.ok().build();
                
        }

  
    }
