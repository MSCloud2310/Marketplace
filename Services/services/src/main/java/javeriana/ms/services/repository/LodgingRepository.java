package javeriana.ms.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javeriana.ms.services.model.Lodging;

public interface LodgingRepository extends JpaRepository<Lodging, Long> {

}

