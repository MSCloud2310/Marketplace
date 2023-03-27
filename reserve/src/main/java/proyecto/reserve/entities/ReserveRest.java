package proyecto.reserve.entities;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserve")
public class ReserveRest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer idUser;
    @Column
    private Integer idService;
    @Column
    private Date startDate;
    @Column
    private Date finishDate;
    @Column
    private Date hour;
}
