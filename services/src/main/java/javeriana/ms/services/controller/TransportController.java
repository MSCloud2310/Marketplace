package javeriana.ms.services.controller;

import javeriana.ms.services.services.TransportService;

import java.util.List;
import java.util.Optional;

import javeriana.ms.services.model.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("services/transports")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @GetMapping
    public ResponseEntity<List<Transport>> getAllTransports() {
        List<Transport> transports = transportService.getAllTransports();
        return new ResponseEntity<>(transports, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transport> updateTransport(@PathVariable Long id, @RequestBody Transport transport) {

        Transport t = transportService.updateTransport(id, transport);
        if (t != null)
            return new ResponseEntity<>(t, HttpStatus.OK);
        return new ResponseEntity<>(transport, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transport> getTransportByServiceId(@PathVariable Long id) {
        Transport transport = transportService.getTransportById(id);
        if (transport != null) {
            return new ResponseEntity<>(transport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transport> deleteTransportByServiceId(@PathVariable Long id) {
        Transport transport = transportService.getTransportById(id);
        if (transport != null) {
            transportService.deleteTransport(id);
            return new ResponseEntity<>(transport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Transport> createTransport(@RequestBody Transport transport) {
        transportService.addTransport(transport);
        return new ResponseEntity<>(transport, HttpStatus.CREATED);
    }


}
