package javeriana.ms.services.services;

import javeriana.ms.services.model.Servicio;
import javeriana.ms.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

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
            existingService.setAddress(service.getAddress());
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