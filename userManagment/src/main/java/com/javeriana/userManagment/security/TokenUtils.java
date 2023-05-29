package com.javeriana.userManagment.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
    
    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfycaRHxhdb9zURb2rf8e7Ud";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public static String createToken(String email, String role, Long id){

        Long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
 
        return Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .claim("id", id)
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
            .compact();
    }

    public static List<String> getClaims(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(token).getBody();
        List<String> claimsArray = new ArrayList<String>();
        claimsArray.add(claims.getSubject());
        claimsArray.add((String) claims.get("role"));
        claimsArray.add((String) claims.get("id").toString());
        
        return claimsArray;
    }


    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

        } catch (JwtException e) {
            
            return null; 
        }
    }

    public static String validateToken(String token) {
        String email =  Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        return email;
    }
    
}
