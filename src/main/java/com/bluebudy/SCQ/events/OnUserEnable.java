/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.events;

import com.bluebudy.SCQ.domain.User;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Alexandre
 */
public class OnUserEnable extends ApplicationEvent {
    
    private String appUrl;
    private User user;
 
    public OnUserEnable(
      User user, String appUrl) {
        super(user);
        
        this.user = user;
      
        this.appUrl = appUrl;
    }
    
    // standard getters and setters

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

      public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}