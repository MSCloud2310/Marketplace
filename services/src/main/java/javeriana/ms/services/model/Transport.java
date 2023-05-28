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
    @Column(name = "pickup_location",nullable = false)
    private String pickup_location;
    @Column(name = "destination",nullable = false)
    private String destination;
    @Column(name ="route_url")
    private String route_url;
    @Column(name = "capacity",nullable = false)
    private Integer capacity;


    // constructor, getters, and setters
}
