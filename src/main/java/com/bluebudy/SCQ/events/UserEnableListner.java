/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.events;

import com.bluebudy.SCQ.servicesInterfaces.IUserService;

import javax.mail.MessagingException;

import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.services.ScqMailSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexandre
 */
@Component
public class UserEnableListner implements ApplicationListener<OnUserEnable> {
 
    @Autowired
    private IUserService service;
 
    @Autowired
    private ScqMailSupportService mailSender;
    
    @Value("${scq.dominio.http}")
    private String dominio;
 
    @Override
    public void onApplicationEvent(OnUserEnable event) {
        try {
            this.enableAdminConfirm(event);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void enableAdminConfirm(OnUserEnable event) throws MessagingException {
        User user = event.getUser();
        
        String userId = user.getId().toString();
        String recipientAddress = "scq.supporte@cationbrasil.com.br";
        String subject = "Promote to Admin Authorize";
        String confirmationUrl = dominio+"/user/adminRoleGrant/"+userId;
        String message = "Clique no link para promover o usuario " + user.getName() + " para Administrador?" ;
    
        mailSender.sendMail(recipientAddress,subject,message,confirmationUrl,event.getAppUrl(),null);
    }
 
   
}