package com.javeriana.ms.contenidos.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer serviceID;

    @Column
    private Integer providerID;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String type;

    @Column
    private String description;

    @Column (nullable = false)
    private String data;

    @Column
    private Date creatio_date;

    @Column
    private boolean status = true;
}
