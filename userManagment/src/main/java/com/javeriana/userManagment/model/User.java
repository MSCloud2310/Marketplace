package com.javeriana.userManagment.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class User { 

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;    

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String photoUrl;

    public User() {

    }

    public User(String email, String password, String firstName, String lastName, Integer age, Role role, String photoUrl) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.role = role;
        this.photoUrl = photoUrl;
    }
}
