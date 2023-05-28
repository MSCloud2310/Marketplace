package javeriana.ms.services.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import javeriana.ms.services.model.Servicio;
import javeriana.ms.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import java.util.*;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Servicio> getAllServices() {
        return serviceRepository.findAll();
    }


    public Servicio getServiceById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    public Servicio createService(Servicio service) {
        return serviceRepository.save(service);
    }


    public Servicio updateService(Long id, Servicio service) {
        Servicio existingService = serviceRepository.findById(id).orElse(null);
        if (existingService != null) {
            existingService.setName(service.getName());
            existingService.setDescription(service.getDescription());
            existingService.setPrice(service.getPrice());
            existingService.setState(service.getState());
            existingService.setCity(service.getCity());
            existingService.setCountry(service.getCountry());
            existingService.setPhone(service.getPhone());
            existingService.setImage(service.getImage());
            return serviceRepository.save(existingService);
        }
        return null;
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}