package com.javeriana.userManagment.model;

import lombok.Data;

@Data
public class Auth {
    
    private String username;
    private String password;

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
