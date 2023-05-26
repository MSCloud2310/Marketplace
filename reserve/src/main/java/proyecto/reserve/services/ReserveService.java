package proyecto.reserve.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.reserve.entities.ReserveRest;
import proyecto.reserve.repository.ReserveRepositoryRest;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



import org.json.JSONArray;
import org.json.JSONObject;



@Service
public class ReserveService {
    @Autowired
    private ReserveRepositoryRest reserveRepositoryRest;



    private static final String API_KEY = "5309e16dcae548cff50d5bd455159403";



    public void addReserve(ReserveRest reserve) {
        reserveRepositoryRest.save(reserve);
    }



    public ReserveRest deleteReserve(Integer id) {
        reserveRepositoryRest.deleteById(id);
        return null;
    }



    public ReserveRest getReserveById(Integer id) {
        return reserveRepositoryRest.findById(id).orElse(null);
    }



    public List<ReserveRest> getReserves() {
        return reserveRepositoryRest.findAll();
    }



    public void updateReserve(Integer id, ReserveRest reserve) {
        reserveRepositoryRest.save(reserve);
    }



    public String getWeatherReport(double latitude, double longitude, String startDate, String endDate) throws IOException {
        String currentWeather = getCurrentWeather(latitude, longitude);
        JSONArray weatherForecast = getWeatherForecast(latitude, longitude, startDate, endDate);

        // Aquí puedes procesar los datos del clima y generar el informe
        StringBuilder report = new StringBuilder();
        report.append("Informe del clima:\n\n");
        report.append("Clima actual:\n");
        report.append(currentWeather).append("\n\n");
        report.append("Pronóstico desde ").append(startDate).append(" hasta ").append(endDate).append(":\n");
        report.append(weatherForecast).append("\n");

        return report.toString();
    }



    private String getCurrentWeather(double latitude, double longitude) throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
        return getWeatherData(url);
    }



    private JSONArray getWeatherForecast(double latitude, double longitude, String startDate, String endDate) throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
        JSONObject weatherData = new JSONObject(getWeatherData(url));

        // Filtrar el pronóstico para el rango de fechas especificado
        JSONArray forecast = weatherData.getJSONArray("list");
        JSONArray filteredForecast = new JSONArray();

        for (int i = 0; i < forecast.length(); i++) {
            JSONObject weatherEntry = forecast.getJSONObject(i);
            String dateTime = weatherEntry.getString("dt_txt");

            // Verificar si la fecha está dentro del rango especificado
            if (dateTime.compareTo(startDate) >= 0 && dateTime.compareTo(endDate) <= 0) {
                filteredForecast.put(weatherEntry);
            }
        }

        return filteredForecast;
    }



    private String getWeatherData(String url) throws IOException {
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");



        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;



        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();



        return response.toString();
    }
}