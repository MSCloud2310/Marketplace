package javeriana.ms.services.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javeriana.ms.services.model.Lodging;
import javeriana.ms.services.repository.LodgingRepository;
import org.springframework.web.client.RestTemplate;

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
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://restcountries.com/v3.1/name/";
        String requestUrl = apiUrl + lodging.getCountry();
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(requestUrl, Object[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Object[] object = responseEntity.getBody();
                Map<String, Object> responseMap = (Map<String, Object>) object[0];
                ArrayList<Double> a = (ArrayList<Double>) responseMap.get("latlng");
                ArrayList<String> capital = (ArrayList<String>) responseMap.get("capital");
                lodging.setCurrencies(responseMap.get("currencies").toString());
                lodging.setRegion(responseMap.get("region").toString());
                lodging.setLatitude(a.get(0));
                lodging.setLongitude(a.get(1));
                lodging.setCapital(capital.get(0));
                String u = get(lodging.getCity(), lodging.getLodging_name());
                if (!u.equals("")) {
                    lodging.setMap_point(u);
                }
            }
        } catch (Exception e) {
            String u = get(lodging.getCity(), lodging.getLodging_name());
            if (!u.equals("")) {
                lodging.setMap_point(u);
            }
            System.out.print(e.toString());
        }

        return lodgingRepository.save(lodging);
    }

    public String get(String city, String name) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + city + " " + name;
        String u = "";
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(apiUrl, Object[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Object[] object = responseEntity.getBody();
                Map<String, Object> responseMap = (Map<String, Object>) object[0];
                String lat = responseMap.get("lat").toString();
                String lon = responseMap.get("lon").toString();
                String url = "https://www.openstreetmap.org/#map=19/" + lat + "/" + lon;
                u = url;
            }

        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return u;
    }

    public Lodging updateLodging(Long id, Lodging lodging) {
        Optional<Lodging> optionalLodging = lodgingRepository.findById(id);
        if (optionalLodging.isPresent()) {
            Lodging tmp = optionalLodging.get();
            lodging.setCurrencies(tmp.getCurrencies());
            lodging.setLatitude(tmp.getLatitude());
            lodging.setLongitude(tmp.getLongitude());
            lodging.setRegion(tmp.getRegion());
            lodging.setCapital(tmp.getCapital());
            String u = get(lodging.getCity(), lodging.getLodging_name());
            if (!u.equals("")) {
                lodging.setMap_point(u);
            }
            lodging.setMap_point(u);
            return lodgingRepository.save(lodging);
        } else {
            return null;
        }
    }


    public void deleteLodging(Long id) {
        lodgingRepository.deleteById(id);
    }

}

