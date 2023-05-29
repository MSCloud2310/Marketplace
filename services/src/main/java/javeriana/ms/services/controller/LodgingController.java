package javeriana.ms.services.controller;

import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javeriana.ms.services.services.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Lodging> createLodging(@RequestBody Lodging lodging, @RequestHeader("Authorization") String authorizationHeader) {

        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROLE_PROVIDER")) {
                Lodging createdLodging = lodgingService.createLodging(lodging);
                createdLodging.setProviderid(Long.valueOf(data.get(2)));
                return new ResponseEntity<>(createdLodging, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Lodging> updateLodging(@PathVariable Long id, @RequestBody Lodging lodging, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROLE_PROVIDER")) {
                Lodging updatedLodging = lodgingService.updateLodging(id, lodging);
                return ResponseEntity.ok(updatedLodging);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Lodging> deleteLodging(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROLE_PROVIDER")) {
                Optional<Lodging> lodging = lodgingService.getLodgingById(id);
                if (lodging.isPresent()) {
                    lodgingService.deleteLodging(id);
                    return ResponseEntity.ok(lodging.get());
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

    }

}
