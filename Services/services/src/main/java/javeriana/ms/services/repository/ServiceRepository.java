package javeriana.ms.services.repository;

import javeriana.ms.services.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Servicio, Long> {
}

