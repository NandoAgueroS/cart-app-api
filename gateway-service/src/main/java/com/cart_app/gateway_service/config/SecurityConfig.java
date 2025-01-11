package com.cart_app.gateway_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationConverter jwtAuthenticationConverter;
    private String userUrlBase = "/api/users";
    private String cartUrlBase = "/api/carts";
    private String productUrlBase = "/api/products";
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(http -> http
                        .pathMatchers(HttpMethod.GET, userUrlBase).hasRole("admin_client_role")
                        .pathMatchers(HttpMethod.GET, userUrlBase.concat("/{username}")).hasRole("admin_client_role")
                        .pathMatchers(HttpMethod.POST, userUrlBase).permitAll()
                        .pathMatchers(HttpMethod.PUT, userUrlBase).authenticated()
                        .pathMatchers(HttpMethod.DELETE, userUrlBase.concat("/{userId}")).authenticated()

                        .pathMatchers(HttpMethod.GET, cartUrlBase).hasRole("user_client_role")
                        .pathMatchers(HttpMethod.GET, cartUrlBase.concat("/detailed")).hasRole("user_client_role")
                        .pathMatchers(HttpMethod.GET, cartUrlBase.concat("/total")).hasRole("user_client_role")
                        .pathMatchers(HttpMethod.POST, cartUrlBase).hasRole("user_client_role")
                        .pathMatchers(HttpMethod.PATCH, cartUrlBase.concat("/remove-item/{productId}")).hasRole("user_client_role")
                        .pathMatchers(HttpMethod.PATCH, cartUrlBase.concat("/add-item")).hasRole("user_client_role")

                        .pathMatchers(HttpMethod.GET, productUrlBase).authenticated()
                        .pathMatchers(HttpMethod.GET, productUrlBase.concat("/all-by-id")).denyAll()
                        .pathMatchers(HttpMethod.GET, productUrlBase.concat("/{id}")).authenticated()
                        .pathMatchers(HttpMethod.POST, productUrlBase).hasRole("admin_client_role")
                        .pathMatchers(HttpMethod.PUT, productUrlBase.concat("/{id}")).hasRole("admin_client_role")
                        .pathMatchers(HttpMethod.DELETE, productUrlBase.concat("/{id}")).hasRole("admin_client_role")

                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .build();
    }
}
