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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javeriana.ms.services.model.Lodging;
import javeriana.ms.services.repository.LodgingRepository;

@Service
public class LodgingService {

    @Autowired
    private LodgingRepository lodgingRepository;

    public List<Lodging> getAllLodgings() {
        return lodgingRepository.findAll();
    }

    public Optional<Lodging> getLodgingById(Long id) {
        return lodgingRepository.findById(id);
    }

    public Lodging createLodging(Lodging lodging) {
        try {
            String nombrePais = lodging.getCountry();
            String informacionPais = obtenerInformacionPais(nombrePais);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(informacionPais);

            // Obtener la ciudad
            String capital = String.valueOf(jsonNode.get(0).get("capital"));
            lodging.setCapital(capital);
            String currency = String.valueOf(jsonNode.get(0).get("currencies"));
            lodging.setCurrencies(currency);
            String continent = String.valueOf(jsonNode.get(0).get("continents"));
            lodging.setContinents(continent);
            String postalCode = String.valueOf(jsonNode.get(0).get("postalCode"));
            lodging.setPostalCode(postalCode);

            // Agregar la información recibida del país al servicio
            return lodgingRepository.save(lodging);


        } catch (IOException e) {
            e.printStackTrace();

        }
        return lodgingRepository.save(lodging);
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

    public Lodging updateLodging(Long id, Lodging lodging) {
        Optional<Lodging> optionalLodging = lodgingRepository.findById(id);
        if (optionalLodging.isPresent()) {
            Lodging lodgingToUpdate = optionalLodging.get();
            lodgingToUpdate.setRooms(lodging.getRooms());
            lodgingToUpdate.setChef(lodging.isChef());
            lodgingToUpdate.setParking(lodging.isParking());
            lodgingToUpdate.setLaundry(lodging.isLaundry());
            lodgingToUpdate.setPet_friendly(lodging.isPet_friendly());
            return lodgingRepository.save(lodging);
        } else {
            return null;
        }
    }

    public void deleteLodging(Long id) {
        lodgingRepository.deleteById(id);
    }

}

