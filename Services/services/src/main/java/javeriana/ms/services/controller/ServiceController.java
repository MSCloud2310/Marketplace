package javeriana.ms.services.controller;

import javeriana.ms.services.model.Comment;
import javeriana.ms.services.model.Servicio;
import javeriana.ms.services.services.CommentService;
import javeriana.ms.services.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Servicio>> getAllServices() {
        List<Servicio> services = serviceService.getAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByService(@PathVariable Long id) {
        List<Comment> comments = commentService.getComments();
        List<Comment> commentByservice = new ArrayList<>();
        for(Comment c : comments){
            if(c.getServiceid() == id){
                commentByservice.add(c);
            }
        }
        return new ResponseEntity<>(commentByservice, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServiceById(@PathVariable Long id) {
        Servicio service = serviceService.getServiceById(id);
        if (service != null) {
            return new ResponseEntity<>(service, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}