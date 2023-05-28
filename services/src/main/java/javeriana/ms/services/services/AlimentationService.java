package javeriana.ms.services.services;

import javeriana.ms.services.model.Alimentation;
import javeriana.ms.services.repository.AlimentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

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

    public void addAlimentation(Alimentation alimentation) {
        // Agregar la información recibida del país al servicio
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://restcountries.com/v3.1/name/";
        String requestUrl = apiUrl + alimentation.getCountry();
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(requestUrl, Object[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Object[] object = responseEntity.getBody();
                Map<String, Object> responseMap = (Map<String, Object>) object[0];
                ArrayList<Double> a = (ArrayList<Double>) responseMap.get("latlng");
                ArrayList<String> capital = (ArrayList<String>) responseMap.get("capital");
                alimentation.setCurrencies(responseMap.get("currencies").toString());
                alimentation.setRegion(responseMap.get("region").toString());
                alimentation.setLatitude(a.get(0));
                alimentation.setLongitude(a.get(1));
                alimentation.setCapital(capital.get(0));
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        alimentationRepository.save(alimentation);
    }

    public Alimentation updateAlimentation(Long id, Alimentation alimentation) {
        Optional<Alimentation> optionalAlimentation = alimentationRepository.findById(id);
        if (optionalAlimentation.isPresent()) {
            Alimentation tmp = optionalAlimentation.get();
            alimentation.setCurrencies(tmp.getCurrencies());
            alimentation.setLatitude(tmp.getLatitude());
            alimentation.setLongitude(tmp.getLongitude());
            alimentation.setRegion(tmp.getRegion());
            alimentation.setCapital(tmp.getCapital());
            return alimentationRepository.save(alimentation);
        }
        return null;

    }

    public void deleteAlimentation(Long id) {
        alimentationRepository.deleteById(id);
    }

}