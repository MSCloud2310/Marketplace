package javeriana.ms.services.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "alimentation")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Alimentation extends Servicio {

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "buffet",nullable = true)
    private boolean buffet;

    @Column(name = "bar",nullable = true)
    private boolean bar;
}