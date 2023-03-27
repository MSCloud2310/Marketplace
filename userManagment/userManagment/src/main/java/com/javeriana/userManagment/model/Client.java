package com.javeriana.userManagment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client extends User{

    @Column(unique = true)
    private String cedula;

    public Client() {
       
    }

    public Client(String username, String password, String name, String lastName, Integer age, String photoUrl, String cedula) {
        super(username, password, name, lastName, age, photoUrl);
        this.cedula = cedula;
    }
}
