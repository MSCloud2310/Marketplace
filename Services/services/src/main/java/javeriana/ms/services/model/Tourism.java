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
@Table(name = "tourism")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Tourism  extends  Servicio{

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "additional_info", nullable = true)
    private String additional_info;
}

