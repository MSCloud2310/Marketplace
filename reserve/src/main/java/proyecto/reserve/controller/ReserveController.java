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
    public ResponseEntity<ReserveRest> createReserve(@RequestBody ReserveRest reserve) {
        OkHttpClient ok = new OkHttpClient();
        String urlservice = env.getProperty("urlService")+reserve.getIdService();
        String urluser = env.getProperty("urlClient")+reserve.getIdUser();
        System.out.println(" llego "+ urluser);
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
            System.out.println("responseeee "+response);
            try(Response responseUser = ok.newCall(requestUser).execute()){
                // Verificar que la respuesta sea exitosa
                if (!responseUser.isSuccessful()) {
                    throw new IOException("Código de respuesta inesperado: " + response);
                }

                // Obtener el cuerpo de la respuesta como una cadena JSON
                String responseBody = response.body().string();
                String responseBodyUser = responseUser.body().string();
                System.out.println(" respuesta "+responseBody);
                System.out.println(" respuestausser "+responseBodyUser);
                reserveService.addReserve(reserve);
                return new ResponseEntity<>(reserve, HttpStatus.CREATED);
            }catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReserveRest> updateReserve(@PathVariable Integer id, @RequestBody ReserveRest reserve) {
        if (reserveService.getReserveById(id) != null) {
            OkHttpClient ok = new OkHttpClient();
            String urlservice = "http://localhost:8080/services/"+reserve.getIdService();
            String urluser = "http://localhost:9001/user/client/"+reserve.getIdUser();
            System.out.println(" llego "+ urluser);
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
                System.out.println("responseeee "+response);
                try(Response responseUser = ok.newCall(requestUser).execute()){
                    // Verificar que la respuesta sea exitosa
                    if (!responseUser.isSuccessful()) {
                        throw new IOException("Código de respuesta inesperado: " + response);
                    }

                    // Obtener el cuerpo de la respuesta como una cadena JSON
                    String responseBody = response.body().string();
                    String responseBodyUser = responseUser.body().string();
                    System.out.println(" respuesta update"+responseBody);
                    System.out.println(" respuestausser update"+responseBodyUser);
                    reserveService.updateReserve(id, reserve);
                    return new ResponseEntity<>(reserve, HttpStatus.CREATED);
                }catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReserveRest> deleteReserveById(@PathVariable Integer id) {
        ReserveRest reserveRest = reserveService.getReserveById(id);
        if (reserveRest!= null) {
            reserveService.deleteReserve(id);
            return new ResponseEntity<>(reserveRest, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
