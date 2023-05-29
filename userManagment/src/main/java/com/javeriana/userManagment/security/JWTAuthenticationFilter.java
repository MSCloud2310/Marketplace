package com.javeriana.userManagment.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javeriana.userManagment.model.AuthCredentials;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, 
                                                HttpServletResponse response) throws AuthenticationException {
      
        AuthCredentials authCredentials = new AuthCredentials();
 
        try {     
            authCredentials = new ObjectMapper().readValue((request.getReader()), AuthCredentials.class);

        } catch (IOException e) {  } 


        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
            authCredentials.getEmail(),
            authCredentials.getPassword()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetailsImpl.getUsername(), userDetailsImpl.getRole(), userDetailsImpl.getId());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();
       
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        response.addHeader("Failed", failed.getMessage());

        super.unsuccessfulAuthentication(request, response, failed);
    }
}
