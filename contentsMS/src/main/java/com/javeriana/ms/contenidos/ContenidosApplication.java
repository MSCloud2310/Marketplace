package com.javeriana.ms.contenidos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
@EnableDiscoveryClient
public class ContenidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContenidosApplication.class, args);
	}

}
