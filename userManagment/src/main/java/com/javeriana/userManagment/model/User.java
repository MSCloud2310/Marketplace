package com.javeriana.userManagment.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String name;
    private String lastName;
    private Integer age;
    private String description;
    private String photoUrl;

    public User() {

    }

    public User(String username, String password, String name, String lastName, Integer age, String description, String photoUrl) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.description = description;
        this.photoUrl = photoUrl;
    }

}
