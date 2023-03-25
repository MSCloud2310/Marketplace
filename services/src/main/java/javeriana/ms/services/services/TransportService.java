package javeriana.ms.services.services;

import java.util.List;
import java.util.Optional;

import javeriana.ms.services.model.Transport;
import javeriana.ms.services.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    public Transport getTransportById(Long id) {
        return transportRepository.findById(id).orElse(null);
    }

    public void addTransport(Transport transport) {
        transportRepository.save(transport);
    }

    public Transport updateTransport(Long id, Transport transport) {
        Optional<Transport> t = transportRepository.findById(id);
        if (t.isPresent()) {
            Transport update = t.get();
            update.setDeparture_time(transport.getDeparture_time());
            update.setType(transport.getType());
            update.setArrival_time(transport.getArrival_time());
            update.setRoute(transport.getRoute());
            update.setCapacity(transport.getCapacity());
            transportRepository.save(update);
            return  transport;
        }
        return null;
    }
    public void deleteTransport(Long id) {
        transportRepository.deleteById(id);
    }
}
