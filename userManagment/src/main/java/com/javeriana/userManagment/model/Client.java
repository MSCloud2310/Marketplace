package com.javeriana.userManagment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "clients")
@EqualsAndHashCode(callSuper=true)
public class Client extends User{

    @Column(unique = true)
    private String cedula;

    public Client() {
       
    }

    public Client(String username, String password, String firstName, String lastName, Integer age, Role role, String photoUrl, String cedula) {
        super(username, password, firstName, lastName, age, role, photoUrl);
        this.cedula = cedula;
    }

}
