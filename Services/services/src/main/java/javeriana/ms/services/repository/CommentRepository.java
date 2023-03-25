package javeriana.ms.services.repository;

import javeriana.ms.services.model.Alimentation;
import javeriana.ms.services.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
