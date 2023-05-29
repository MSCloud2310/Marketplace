package javeriana.ms.services.controller;

import javeriana.ms.services.model.Tourism;
import javeriana.ms.services.services.TokenUtils;
import javeriana.ms.services.services.TourismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.*;

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
    public ResponseEntity<Tourism> createTourism(@RequestBody Tourism tourism, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROL_PROVEEDOR")) {
                tourism.setId(Long.valueOf(data.get(2)));
                Tourism createdTourism = tourismService.createTourism(tourism);
                return new ResponseEntity<>(createdTourism, HttpStatus.CREATED);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tourism> updateTourism(@PathVariable("id") Long id, @RequestBody Tourism tourism, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROL_PROVEEDOR")) {
                Tourism updatedTourism = tourismService.updateTourism(id, tourism);
                if (updatedTourism == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(updatedTourism, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourism(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROL_PROVEEDOR")) {
                tourismService.deleteTourism(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
