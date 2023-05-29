package com.javeriana.userManagment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.javeriana.userManagment.model.AuthResponse;
import com.javeriana.userManagment.model.Client;
import com.javeriana.userManagment.model.Provider;
import com.javeriana.userManagment.security.TokenUtils;
import com.javeriana.userManagment.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    //REGISTER CLIENT
    @PostMapping("/register/client")
    public ResponseEntity<?> postUser(@RequestBody Client client){
        AuthResponse clientCreated = userService.createClientUser(client);
        if(clientCreated == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        return ResponseEntity.ok(clientCreated);
    }

    //REGISTER PROVIDER
    @PostMapping("/register/provider")
    public ResponseEntity<?> postProvider(@RequestBody Provider provider){
        AuthResponse providerCreated = userService.createProviderUser(provider);
        if(providerCreated == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        return ResponseEntity.ok(providerCreated);
    }

    //VALIDATE TOKEN
    @GetMapping("/validateToken")
    public AuthResponse validateToken(@RequestParam String token){
        return userService.validateToken(token);
    }
}
