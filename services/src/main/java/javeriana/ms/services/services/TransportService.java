package javeriana.ms.services.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            System.out.println("r"+ results.length);
        // Extrae la ubicación geográfica (latitud y longitud) del primer resultado
            if (results.length > 0) {
                System.out.println("r2"+ results[0].geometry.location.toString());
                return results[0].geometry.location.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void addTransport(Transport transport) {

        try {
            String address = "Carrera 7A #135-78"; // Supongamos que la dirección está almacenada en la propiedad "route" de la entidad Transport
            String geolocation = getGeolocationByAddress(address);
            System.out.println("1 "+ geolocation);
            if (geolocation != null) {
                transport.setGeolocation(geolocation); // Supongamos que tienes una propiedad "geolocation" en la entidad Transport para guardar la ubicación geográfica
            }
            String nombrePais = transport.getCountry();
            String informacionPais = obtenerInformacionPais(nombrePais);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(informacionPais);

            // Obtener la ciudad
            String capital = String.valueOf(jsonNode.get(0).get("capital"));
            transport.setCapital(capital);
            String currency = String.valueOf(jsonNode.get(0).get("currencies"));
            transport.setCurrencies(currency);
            String continent = String.valueOf(jsonNode.get(0).get("continents"));
            transport.setContinents(continent);
            String postalCode = String.valueOf(jsonNode.get(0).get("postalCode"));
            transport.setPostalCode(postalCode);

            // Agregar la información recibida del país al servicio
            System.out.println("2 "+ transport.getGeolocation());
            transportRepository.save(transport);


        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    private String obtenerInformacionPais(String nombrePais) throws IOException {
        String apiUrl = "https://restcountries.com/v3.1/name/" + nombrePais;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } else {
            // Manejar el caso de error en la solicitud
            throw new IOException("Error en la solicitud. Código de respuesta: " + responseCode);
        }
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
