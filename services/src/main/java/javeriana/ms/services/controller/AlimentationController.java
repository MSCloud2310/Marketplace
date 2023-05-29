package javeriana.ms.services.controller;

import javeriana.ms.services.model.Alimentation;
import javeriana.ms.services.model.Transport;
import javeriana.ms.services.services.AlimentationService;
import javeriana.ms.services.services.ServiceService;
import javeriana.ms.services.services.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("services/alimentation")
public class AlimentationController {
    @Autowired
    private AlimentationService alimentationService;

    @GetMapping
    public ResponseEntity<List<Alimentation>> getAllAlimentation() {
        return ResponseEntity.ok(alimentationService.getAllAlimentation());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alimentation> updateAlimentation(@PathVariable Long id, @RequestBody Alimentation alimentation, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROL_PROVEEDOR")) {
                if (alimentationService.getAlimentationById(id) != null) {
                    Alimentation tmp = alimentationService.updateAlimentation(id, alimentation);
                    return new ResponseEntity<>(tmp, HttpStatus.OK);
                }
                return new ResponseEntity<>(alimentation, HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(alimentation, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(alimentation, HttpStatus.INTERNAL_SERVER_ERROR);

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
    public ResponseEntity<Alimentation> deleteAlimentationByServiceId(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROL_PROVEEDOR")) {
                Alimentation alimentation = alimentationService.getAlimentationById(id);
                if (alimentation != null) {
                    alimentationService.deleteAlimentation(id);
                    return new ResponseEntity<>(alimentation, HttpStatus.OK);
                } else
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<Alimentation> createAlimentation(@RequestBody Alimentation
                                                                   alimentation, @RequestHeader("Authorization") String authorizationHeader) {
        List<String> data;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            data = TokenUtils.getClaims(authorizationHeader);
            if (data.get(1).equals("ROL_PROVEEDOR")) {
                alimentation.setId(Long.valueOf(data.get(2)));
                alimentationService.addAlimentation(alimentation);
                return new ResponseEntity<>(alimentation, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}