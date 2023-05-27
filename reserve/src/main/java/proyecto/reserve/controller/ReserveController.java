package proyecto.reserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.reserve.entities.ReserveRest;
import proyecto.reserve.services.ReserveService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    @Autowired
    private ReserveService reserveService;
    @Autowired
    Environment env;

    @PostMapping
    public ResponseEntity<?> createReserve(@RequestBody ReserveRest reserve) {
        OkHttpClient ok = new OkHttpClient();
        String urlservice = env.getProperty("urlService") + reserve.getIdService();
        String urluser = env.getProperty("urlClient") + reserve.getIdUser();
        System.out.println(" llego " + urluser);
        Request request = new Request.Builder()
                .url(urlservice)
                .build();
        Request requestUser = new Request.Builder()
                .url(urluser)
                .build();
        try (Response response = ok.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Código de respuesta inesperado: " + response);
            }
            System.out.println("responseeee " + response);
            try (Response responseUser = ok.newCall(requestUser).execute()) {
                // Verificar que la respuesta sea exitosa
                if (!responseUser.isSuccessful()) {
                    throw new IOException("Código de respuesta inesperado: " + response);
                }

                // Obtener el cuerpo de la respuesta como una cadena JSON
                String responseBody = response.body().string();
                String responseBodyUser = responseUser.body().string();
                System.out.println(" respuesta " + responseBody);
                System.out.println(" respuestausser " + responseBodyUser);
                boolean verificar = false;
                if (reserve.getNumeroTarjeta() != null && reserve.getClaveTarjeta() != null) {
                    verificar = true;
                    reserve.setEstado(true);
                } else {
                    reserve.setEstado(false);
                }
                reserveService.addReserve(reserve);
                if (verificar == true) {
                    
                    return ResponseEntity.status(HttpStatus.CREATED).body("Pago realizado correctamente de la reserva ");
                }
                return ResponseEntity.status(HttpStatus.CREATED).body("Pago NO realizado de la reserva ");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserve(@PathVariable Integer id, @RequestBody ReserveRest reserve) {
        if (reserveService.getReserveById(id) != null) {
            OkHttpClient ok = new OkHttpClient();
            String urlservice = env.getProperty("urlService") + reserve.getIdService();
            String urluser = env.getProperty("urlClient") + reserve.getIdUser();
            System.out.println(" llego " + urluser);
            Request request = new Request.Builder()
                    .url(urlservice)
                    .build();
            Request requestUser = new Request.Builder()
                    .url(urluser)
                    .build();
            try (Response response = ok.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Código de respuesta inesperado: " + response);
                }
                System.out.println("responseeee " + response);
                try (Response responseUser = ok.newCall(requestUser).execute()) {
                    // Verificar que la respuesta sea exitosa
                    if (!responseUser.isSuccessful()) {
                        throw new IOException("Código de respuesta inesperado: " + response);
                    }

                    // Obtener el cuerpo de la respuesta como una cadena JSON
                    String responseBody = response.body().string();
                    String responseBodyUser = responseUser.body().string();
                    System.out.println(" respuesta update" + responseBody);
                    System.out.println(" respuestausser update" + responseBodyUser);
                    boolean verificar = false;
                    if (reserve.getNumeroTarjeta() != null && reserve.getClaveTarjeta() != null) {
                        verificar = true;
                        reserve.setEstado(true);
                    } else {
                        reserve.setEstado(false);
                    }
                    reserveService.updateReserve(id, reserve);
                    if (verificar == true) {
                        return ResponseEntity.status(HttpStatus.CREATED).body("Pago realizado correctamente de la reserva ");
                    }
                    return ResponseEntity.status(HttpStatus.CREATED).body("Pago NO realizado de la reserva ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{longitud}/{latitud}/{startDate}/{endDate}")
    public String reporteDelClima(@PathVariable double longitud,@PathVariable double latitud,
                                @PathVariable String startDate,@PathVariable String endDate) throws IOException {

        return reserveService.getWeatherReport(latitud,longitud,startDate, endDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReserveRest> deleteReserveById(@PathVariable Integer id) {
        ReserveRest reserveRest = reserveService.getReserveById(id);
        if (reserveRest != null) {
            reserveService.deleteReserve(id);
            return new ResponseEntity<>(reserveRest, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReserveRest>> getReserveByUser(@PathVariable Integer id, @RequestParam(name = "state") String state) {
        List<ReserveRest> reserveRest = reserveService.getReserves();
        List<ReserveRest> rt = new ArrayList<>();
        if (state.equals("true")) {
            for (ReserveRest r : reserveRest) {
                if (r.getIdUser() == id && r.isEstado()) {
                    rt.add(r);
                }
            }
        } else {
            for (ReserveRest r : reserveRest) {
                if (r.getIdUser() == id && !r.isEstado()) {
                    rt.add(r);
                }
            }
        }
        return new ResponseEntity<>(rt, HttpStatus.OK);
    }
}
