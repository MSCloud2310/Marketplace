package com.javeriana.userManagment.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String lastName;
    private Integer age;    

    @Column(unique = true)
    private String photoUrl;

    public User() {

    }

    public User(String username, String password, String name, String lastName, Integer age, String photoUrl) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.photoUrl = photoUrl;
    }

}
