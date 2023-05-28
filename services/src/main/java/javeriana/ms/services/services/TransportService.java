package javeriana.ms.services.services;

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
import com.google.maps.GeoApiContext;
import javeriana.ms.services.model.Transport;
import javeriana.ms.services.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.maps.model.GeocodingResult;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.web.client.RestTemplate;


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
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://restcountries.com/v3.1/name/";
        String requestUrl = apiUrl + transport.getCountry();
        System.out.println("REQ:" + requestUrl);
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(requestUrl, Object[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Object[] object = responseEntity.getBody();
                Map<String, Object> responseMap = (Map<String, Object>) object[0];
                ArrayList<Double> a = (ArrayList<Double>) responseMap.get("latlng");
                ArrayList<String> capital = (ArrayList<String>) responseMap.get("capital");
                transport.setCurrencies(responseMap.get("currencies").toString());
                transport.setRegion(responseMap.get("region").toString());
                transport.setLatitude(a.get(0));
                transport.setLongitude(a.get(1));
                transport.setCapital(capital.get(0));
                transport.setPickup_location(transport.getPickup_location());
                transport.setDestination(transport.getDestination());
                String u = url(transport);
                if (!u.equals("")) {
                    transport.setRoute_url(u);
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        transportRepository.save(transport);
    }

    public String url(Transport transport) {
        RestTemplate restTemplate = new RestTemplate();
        String baseApiUrl = "https://www.openstreetmap.org/directions?engine={engine}&route={startLat}%2C{startLng}%3B{endLat}%2C{endLng}";
        String engine = "fossgis_osrm_car";
        String u = "";
        ArrayList<String> tmp = get(transport.getPickup_location());
        ArrayList<String> tmp2 = get(transport.getDestination());

        u = baseApiUrl
                .replace("{engine}", engine)
                .replace("{startLat}", tmp.get(0))
                .replace("{startLng}", tmp.get(1))
                .replace("{endLat}", tmp2.get(0))
                .replace("{endLng}", tmp2.get(1));
        return u;
    }

    public ArrayList<String> get(String point) {
        ArrayList<String> rt = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + point;
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(apiUrl, Object[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Object[] object = responseEntity.getBody();
                Map<String, Object> responseMap = (Map<String, Object>) object[0];
                String lat = responseMap.get("lat").toString();
                String lon = responseMap.get("lon").toString();
                rt.add(lat);
                rt.add(lon);
            }

        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return rt;
    }

    public Transport updateTransport(Long id, Transport transport) {
        Optional<Transport> t = transportRepository.findById(id);
        if (t.isPresent()) {
            Transport tmp = t.get();
            transport.setCurrencies(tmp.getCurrencies());
            transport.setLatitude(tmp.getLatitude());
            transport.setLongitude(tmp.getLongitude());
            transport.setRegion(tmp.getRegion());
            transport.setCapital(tmp.getCapital());
            transportRepository.save(transport);
            return transport;
        }
        return null;
    }

    public void deleteTransport(Long id) {
        transportRepository.deleteById(id);
    }
}
