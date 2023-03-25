package com.javeriana.userManagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client extends User{

    private String cedula;

    public Client() {
       
    }

    public Client(String username, String password, String name, String lastName, Integer age, String description, String photoUrl, String cedula) {
        super(username, password, name, lastName, age, description, photoUrl);
        this.cedula = cedula;
    }

}
