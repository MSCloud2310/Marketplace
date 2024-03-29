package javeriana.ms.gateway.config;

import javeriana.ms.gateway.dto.UserDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }


    public static class Config {
        // Add configuration properties if needed
    }

    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

                throw new RuntimeException("Missing auth header");
            }

            String authToken = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authToken.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect structure");
            }

            return webClientBuilder.build()
                    .get()
                    .uri(System.getenv("USERS_URL") + "/auth/validateToken?token=" + parts[1])
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .map(userDto -> {
                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("bearerToken", userDto.getToken()).build();
                        return exchange.mutate().request(modifiedRequest).build();

                    }).flatMap(chain::filter);
        };
    }
}
