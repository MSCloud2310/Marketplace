package com.javeriana.userManagment.model;

import lombok.Data;

@Data
public class AuthCredentials {
    
    private String email;
    private String password;

    public AuthCredentials(){

    }

    public AuthCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
