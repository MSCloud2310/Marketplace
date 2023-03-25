package javeriana.ms.services.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Entity
@Table(name = "transport")
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Transport extends Servicio {

    @Column(name = "departure_time",nullable = false)
    private Date departure_time;
    @Column(name = "type",nullable = false)
    private String type;
    @Column(name = "arrival_time",nullable = false)
    private Date arrival_time;

    @Column(name = "route",nullable = false)
    private String route;

    @Column(name = "capacity",nullable = false)
    private Integer capacity;

    // constructor, getters, and setters
}
