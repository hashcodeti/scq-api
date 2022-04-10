/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.events;

import com.bluebudy.SCQ.servicesInterfaces.IUserService;
import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.services.ScqMailSupportService;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexandre
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
 
    @Autowired
    private IUserService service;
 
    @Autowired
    private MessageSource messages;
 
    @Autowired
    private ScqMailSupportService mailSender;
    
    @Value("${scq.dominio.http}")
    private String dominio;
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        
        String recipientAddress = event.getUser().getUsername();
        String subject = "Registration Confirmation";
        String confirmationUrl = dominio+"/regitrationConfirm/" + token;
        String message = "Clique no link para confirmar sua conta";
    
        mailSender.sendMail(recipientAddress,subject,message,confirmationUrl,event.getAppUrl(),null);
     
       
    }
}