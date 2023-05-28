package javeriana.ms.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import javeriana.ms.gateway.dto.UserDto;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder wBuilder;

    public AuthFilter(WebClient.Builder wBuilder){
        this.wBuilder = wBuilder;
    }
    
    public static class Config{
        
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Missin auth header");
            }

            String authToken = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authToken.split(" ");

            if(parts.length != 2 || !"Bearer".equals(parts[0])){
                throw new RuntimeException("Incorrect structure");
            }

            return wBuilder.build()
                    .post()
                    .uri("Ruta" + parts[1])
                    .retrieve().bodyToMono(UserDto.class)
                    .map(userDto -> {
                        exchange.getRequest()
                                    .mutate()
                                    .header("");
                        
                        return exchange;
                    }).flatMap(chain::filter);
        };
    }
}
