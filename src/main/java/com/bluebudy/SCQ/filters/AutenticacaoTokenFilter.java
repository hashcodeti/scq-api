/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bluebudy.SCQ.domain.User;
import com.bluebudy.SCQ.repository.UserRepository;
import com.bluebudy.SCQ.services.TokenService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Alexandre
 */
public class AutenticacaoTokenFilter extends OncePerRequestFilter{
    
 
    private TokenService tokenService;
    private UserRepository repository;
    

    public AutenticacaoTokenFilter(TokenService tokenService, UserRepository repository) {
       
        this.tokenService = tokenService;
        this.repository = repository;
    }
    
    

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getRequestURI());


        
       
  
        	 String token  = recuperarToken(request);
             boolean isValido = tokenService.isTokenValido(token);
        	 if(isValido){
                 autenticarCliente(token);
                
             
             }
        	 filterChain.doFilter(request, response);
         
        
           
        
      
        
     
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        } 
        return token.substring(7, token.length());
    }

    private void autenticarCliente(String token) {
        String emailUsuario = tokenService.getIdUsuario(token);
        User usuario = repository.findByMail(emailUsuario);
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    
    
    
}
