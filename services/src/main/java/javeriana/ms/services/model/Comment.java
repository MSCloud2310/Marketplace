package javeriana.ms.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "clientid", unique = true,nullable = false)
    private Long clientid;
    @Column(name = "rate")
    private int rate;
    @Column(name = "description")
    private String description;
    @Column(name = "serviceid")
    private Long serviceid;

}
