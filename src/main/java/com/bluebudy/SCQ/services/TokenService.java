/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluebudy.SCQ.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * @author Alexandre
 */

@Service
public class TokenService {
    
    @Value("${scq.jwt.expiration}")
    private String expiration;
    
    @Value("${scq.jwt.secret}")
    private String secret;

    public String gerarToken(String username) {

        Date hoje = new Date();
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.MILLISECOND, Integer.parseInt(expiration));
        return Jwts.builder()
                .setIssuer("SCQ")
                .setSubject(username)
                .setIssuedAt(hoje)
                .setExpiration(exp.getTime())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
                
     }
    
    
    public boolean isTokenValido(String token){
        
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public String getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        claims.getSubject();
        return String.valueOf(claims.getSubject());
    }
    
    
    
}
