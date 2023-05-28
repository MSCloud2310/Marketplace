package javeriana.ms.services.controller;

import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javeriana.ms.services.model.Lodging;
import javeriana.ms.services.services.LodgingService;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("services/lodging")
public class LodgingController {

    @Autowired
    private LodgingService lodgingService;

    @GetMapping
    public ResponseEntity<List<Lodging>> getAllLodgings() {
        List<Lodging> lodgings = lodgingService.getAllLodgings();
        return new ResponseEntity<>(lodgings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lodging> getLodgingById(@PathVariable Long id) {
        Optional<Lodging> optionalLodging = lodgingService.getLodgingById(id);
        if (optionalLodging.isPresent()) {
            Lodging lodging = optionalLodging.get();
            return new ResponseEntity<>(lodging, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Lodging> createLodging(@RequestBody Lodging lodging) {
        Lodging createdLodging = lodgingService.createLodging(lodging);
        return new ResponseEntity<>(createdLodging, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lodging> updateLodging(@PathVariable Long id, @RequestBody Lodging lodging) {
        Lodging updatedLodging = lodgingService.updateLodging(id, lodging);
        return ResponseEntity.ok(updatedLodging);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLodging(@PathVariable Long id) {
        lodgingService.deleteLodging(id);
        return ResponseEntity.noContent().build();
    }
}
