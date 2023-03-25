package javeriana.ms.services.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lodging")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Lodging extends Servicio {
    @Column(name = "rooms", nullable = false)
    private int rooms;
    @Column(name = "chef")
    private boolean chef;
    @Column(name = "parking")
    private boolean parking;

    @Column(name = "laundry")
    private boolean laundry;

    @Column(name = "pet_friendly")
    private boolean pet_friendly;
}
