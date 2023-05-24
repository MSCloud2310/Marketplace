package javeriana.ms.services.controller;

import javeriana.ms.services.model.Tourism;
import javeriana.ms.services.services.TourismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("services/tourism")
public class TourismController {

    @Autowired
    private TourismService tourismService;

    @GetMapping("")
    public List<Tourism> getAllTourism() {
        return tourismService.getAllTourism();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourism> getTourismById(@PathVariable("id") Long id) {
        Tourism tourism = tourismService.getTourismById(id);
        if (tourism == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tourism, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Tourism> createTourism(@RequestBody Tourism tourism) {
        Tourism createdTourism = tourismService.createTourism(tourism);
        return new ResponseEntity<>(createdTourism, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tourism> updateTourism(@PathVariable("id") Long id, @RequestBody Tourism tourism) {
        Tourism updatedTourism = tourismService.updateTourism(id, tourism);
        if (updatedTourism == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTourism, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourism(@PathVariable("id") Long id) {
        tourismService.deleteTourism(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

