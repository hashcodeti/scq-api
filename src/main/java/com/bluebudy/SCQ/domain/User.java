package com.bluebudy.SCQ.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
public class User implements UserDetails{
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")  
	private Long id;
	
	@Column(unique = true)
	private String mail;

	private String password;
	
	private String name;

	private boolean admin;

	private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(admin){
            return  AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN");
		
        } else {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    
    
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    
    
    
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
       return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
        
        

        
	
	

    
	

}
