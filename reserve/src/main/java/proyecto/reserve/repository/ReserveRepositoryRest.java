package proyecto.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.reserve.entities.Reserve;
import proyecto.reserve.entities.ReserveRest;

public interface ReserveRepositoryRest extends JpaRepository<ReserveRest, Integer> {
}
