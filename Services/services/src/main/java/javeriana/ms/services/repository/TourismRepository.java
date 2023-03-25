package javeriana.ms.services.repository;


import javeriana.ms.services.model.Tourism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourismRepository extends JpaRepository<Tourism, Long> {
}

