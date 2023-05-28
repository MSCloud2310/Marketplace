package javeriana.ms.services.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javeriana.ms.services.model.Tourism;
import javeriana.ms.services.repository.TourismRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://restcountries.com/v3.1/name/";
        String requestUrl = apiUrl + tourism.getCountry();
        System.out.println("REQ:" + requestUrl);
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(requestUrl, Object[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Object[] object = responseEntity.getBody();
                Map<String, Object> responseMap = (Map<String, Object>) object[0];
                ArrayList<Double> a = (ArrayList<Double>) responseMap.get("latlng");
                ArrayList<String> capital = (ArrayList<String>) responseMap.get("capital");
                tourism.setCurrencies(responseMap.get("currencies").toString());
                tourism.setRegion(responseMap.get("region").toString());
                tourism.setLatitude(a.get(0));
                tourism.setLongitude(a.get(1));
                tourism.setCapital(capital.get(0));
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return tourismRepository.save(tourism);
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
