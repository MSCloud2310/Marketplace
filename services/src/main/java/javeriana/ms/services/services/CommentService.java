package javeriana.ms.services.services;

import javeriana.ms.services.model.Comment;
import javeriana.ms.services.model.Servicio;
import javeriana.ms.services.repository.CommentRepository;
import javeriana.ms.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ServiceRepository serviceRepository;


    public Comment createComment(Comment comment) {
        Optional<Servicio> service = serviceRepository.findById(comment.getServiceid());
        if (service.isPresent()) {
            return commentRepository.save(comment);
        }
        return null;
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment commentToUpdate = getCommentById(id);

        if (commentToUpdate == null) {
            return null;
        }
        commentToUpdate.setRate(comment.getRate());
        commentToUpdate.setDescription(comment.getDescription());
        commentToUpdate.setServiceid(comment.getServiceid());
        return commentRepository.save(commentToUpdate);
    }

    public boolean deleteComment(Long id) {
        Comment commentToDelete = getCommentById(id);

        if (commentToDelete == null) {
            return false;
        }

        commentRepository.delete(commentToDelete);
        return true;
    }

}

