package com.javeriana.userManagment.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "providers")
public class Provider extends User{

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String webPage;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SocialNetwork> socialNetworks;

    public Provider() {

    }

    public Provider(String username, String password, String name, String lastName, Integer age, String photoUrl, String phone, String webPage, List<SocialNetwork> socialNetworks) {
        super(username, password, name, lastName, age, photoUrl);
        this.phone = phone;
        this.webPage = webPage;
        this.socialNetworks = socialNetworks;
    }

}
