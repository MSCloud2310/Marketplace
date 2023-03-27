package com.javeriana.userManagment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SocialNetworks")
public class SocialNetwork {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String url;

    @ManyToOne
    @JsonIgnore
    private Provider provider;

    public SocialNetwork() {
    }

    public SocialNetwork(String name, String userName, String url) {
        this.name = name;
        this.userName = userName;
        this.url = url;
    }

    public SocialNetwork(String name, String userName, String url, Provider provider) {
        this.name = name;
        this.userName = userName;
        this.url = url;
        this.provider = provider;
    }

}
