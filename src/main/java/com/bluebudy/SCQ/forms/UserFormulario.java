/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.forms;

import com.bluebudy.SCQ.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Alexandre
 */
public class UserFormulario {
    
	
	@Email @NotEmpty @NotNull
	private String mail;

	@NotEmpty @NotNull
	private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

 

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
        
        
    public User generateUser(PasswordEncoder encoder) {
        User usuario =  new User();
        usuario.setMail(mail);
        usuario.setPassword(encoder.encode(password));
        String[] nameToken = mail.split("@");
        usuario.setName(nameToken[0]);
        usuario.setAdmin(false);
        usuario.setEnabled(false);
        return usuario;
    }
	
   
}
