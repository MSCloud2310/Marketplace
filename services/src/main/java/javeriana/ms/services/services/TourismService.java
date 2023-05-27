package javeriana.ms.services.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javeriana.ms.services.model.Tourism;
import javeriana.ms.services.repository.TourismRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class TourismService {

    @Autowired
    private TourismRepository tourismRepository;

    public List<Tourism> getAllTourism() {
        return tourismRepository.findAll();
    }

    public Tourism getTourismById(Long id) {
        return tourismRepository.findById(id).orElse(null);
    }

    public Tourism createTourism(Tourism tourism) {
        try {
            String nombrePais = tourism.getCountry();
            String informacionPais = obtenerInformacionPais(nombrePais);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(informacionPais);

            // Obtener la ciudad
            String capital = String.valueOf(jsonNode.get(0).get("capital"));
            tourism.setCapital(capital);
            String currency = String.valueOf(jsonNode.get(0).get("currencies"));
            tourism.setCurrencies(currency);
            String continent = String.valueOf(jsonNode.get(0).get("continents"));
            tourism.setContinents(continent);
            String postalCode = String.valueOf(jsonNode.get(0).get("postalCode"));
            tourism.setPostalCode(postalCode);

            // Agregar la información recibida del país al servicio
            return tourismRepository.save(tourism);


        } catch (IOException e) {
            e.printStackTrace();

        }
       return null;
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

    public Tourism updateTourism(Long id, Tourism tourism) {
        Tourism existingTourism = tourismRepository.findById(id).orElse(null);
        if (existingTourism == null) {
            return null;
        }
        existingTourism.setType(tourism.getType());
        existingTourism.setDuration(tourism.getDuration());
        existingTourism.setAdditional_info(tourism.getAdditional_info());
        return tourismRepository.save(existingTourism);
    }

    public void deleteTourism(Long id) {
        tourismRepository.deleteById(id);
    }
}
