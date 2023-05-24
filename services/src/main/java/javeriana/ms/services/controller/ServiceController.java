package javeriana.ms.services.controller;

import javeriana.ms.services.model.Comment;
import javeriana.ms.services.model.Servicio;
import javeriana.ms.services.services.CommentService;
import javeriana.ms.services.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Servicio>> getAllServices() {
        List<Servicio> services = serviceService.getAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByService(@PathVariable Long id) {
        List<Comment> comments = commentService.getComments();
        List<Comment> commentByservice = new ArrayList<>();
        for (Comment c : comments) {
            if (c.getServiceid() == id) {
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

    @GetMapping("/by")
    public ResponseEntity<List<Servicio>> getServiceByQuery(@RequestParam(name = "value") String by) {
        String sql = "SELECT * FROM service WHERE name LIKE ?";
        String parametro = "%" + by + "%";
        List<Servicio> suggestions = jdbcTemplate.query(sql, new Object[]{parametro}, new BeanPropertyRowMapper<>(Servicio.class));
        String sql2 = "SELECT * FROM service WHERE description LIKE ?";
        String parametro2 = "%" + by + "%";
        List<Servicio> suggestions2 = jdbcTemplate.query(sql2, new Object[]{parametro2}, new BeanPropertyRowMapper<>(Servicio.class));
        Set<Servicio> conjunto = new HashSet<>();
        conjunto.addAll(suggestions);
        conjunto.addAll(suggestions2);
        return new ResponseEntity<>(new ArrayList<>(conjunto), HttpStatus.OK);
    }
    

}