package com.bluebudy.SCQ.services;

import java.util.Optional;

import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	public UserRepository ur;
	
	@Autowired
	public CustomUserDetailService(UserRepository userRepository) {
		this.ur = userRepository;
	
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = Optional.ofNullable(ur.findByMail(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
      
                return user;
		
	}
        
        
	
	

}
