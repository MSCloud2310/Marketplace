package javeriana.ms.services.services;

import javeriana.ms.services.model.Alimentation;
import javeriana.ms.services.repository.AlimentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AlimentationService {

    @Autowired
    private AlimentationRepository alimentationRepository;


    public List<Alimentation> getAllAlimentation() {
        return alimentationRepository.findAll();
    }

    public Alimentation getAlimentationById(Long id) {
        return alimentationRepository.findById(id).orElse(null);
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
    public void addAlimentation(Alimentation alimentation) {
        try {
            String nombrePais = alimentation.getCountry();
            String informacionPais = obtenerInformacionPais(nombrePais);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(informacionPais);

            // Obtener la ciudad
            String capital = String.valueOf(jsonNode.get(0).get("capital"));
            alimentation.setCapital(capital);
            String currency = String.valueOf(jsonNode.get(0).get("currencies"));
            alimentation.setCurrencies(currency);
            String continent = String.valueOf(jsonNode.get(0).get("continents"));
            alimentation.setContinents(continent);
            String flag = String.valueOf(jsonNode.get(0).get("flags"));
            alimentation.setFlags(flag);
            String postalCode = String.valueOf(jsonNode.get(0).get("postalCode"));
            alimentation.setPostalCode(postalCode);

            // Agregar la información recibida del país al servicio
            alimentationRepository.save(alimentation);


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void updateAlimentation(Long id, Alimentation alimentation) {
        alimentationRepository.save(alimentation);

    }

    public void deleteAlimentation(Long id) {
        alimentationRepository.deleteById(id);
    }

}