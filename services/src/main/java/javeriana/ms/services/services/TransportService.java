package javeriana.ms.services.services;

import java.util.List;
import java.util.Optional;

import com.google.maps.GeoApiContext;
import javeriana.ms.services.model.Transport;
import javeriana.ms.services.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.maps.model.GeocodingResult;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;



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
    public String getGeolocationByAddress(String address) {
        try {
        // Configura el contexto de la API de Google Maps con tu clave de API
            String apiKey = "AIzaSyBSlFrKxzAUevjlhN9dA4ku1-H6uAB-eTE";
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(apiKey)
                    .build();

        // Realiza la solicitud de geocodificación a la API de Google Maps
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();

        // Extrae la ubicación geográfica (latitud y longitud) del primer resultado
            if (results.length > 0) {
                return results[0].geometry.location.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void addTransport(Transport transport) {
        String address = "Carrera 7A #135-78"; // Supongamos que la dirección está almacenada en la propiedad "route" de la entidad Transport
        String geolocation = getGeolocationByAddress(address);

        if (geolocation != null) {
            transport.setGeolocation(geolocation); // Supongamos que tienes una propiedad "geolocation" en la entidad Transport para guardar la ubicación geográfica
        }
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
