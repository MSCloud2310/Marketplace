package javeriana.ms.services.controller;

import javeriana.ms.services.model.Alimentation;
import javeriana.ms.services.model.Transport;
import javeriana.ms.services.services.AlimentationService;
import javeriana.ms.services.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alimentation")
public class AlimentationController {
    @Autowired
    private AlimentationService alimentationService;

    @GetMapping
    public ResponseEntity<List<Alimentation>> getAllAlimentation() {
        return ResponseEntity.ok(alimentationService.getAllAlimentation());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alimentation> updateAlimentation(@PathVariable Long id, @RequestBody Alimentation alimentation) {
        if (alimentationService.getAlimentationById(id) != null) {
            alimentationService.updateAlimentation(id, alimentation);
            return new ResponseEntity<>(alimentation, HttpStatus.OK);
        }
        return new ResponseEntity<>(alimentation, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alimentation> getAlimentationByServiceId(@PathVariable Long id) {
        Alimentation alimentation = alimentationService.getAlimentationById(id);
        if (alimentation != null) {
            return new ResponseEntity<>(alimentation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Alimentation> deleteAlimentationByServiceId(@PathVariable Long id) {
        Alimentation alimentation = alimentationService.getAlimentationById(id);
        if (alimentation!= null) {
            alimentationService.deleteAlimentation(id);
            return new ResponseEntity<>(alimentation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Alimentation> createAlimentation(@RequestBody Alimentation alimentation) {
        alimentationService.addAlimentation(alimentation);
        return new ResponseEntity<>(alimentation, HttpStatus.CREATED);
    }

}