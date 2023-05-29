package javeriana.ms.services.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "service")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "providerid", nullable = true)
    private Long providerid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "phone", nullable = false)
    private Integer phone;

    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "latitude", nullable = true)
    private Double latitude;
    @Column(name = "longitude", nullable = true)
    private Double longitude;
    @Column(name = "currencies", nullable = true)
    private String currencies;
    @Column(name = "capital", nullable = true)
    private String capital;
    @Column(name = "region", nullable = true)
    private String region;

    // getters and setters
}
