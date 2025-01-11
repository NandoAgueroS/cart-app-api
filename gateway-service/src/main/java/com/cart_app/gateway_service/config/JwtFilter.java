package com.cart_app.gateway_service.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    private Mono<String> getUserId(){
        return ReactiveSecurityContextHolder.getContext()
                .filter(securityContext -> securityContext.getAuthentication() != null)
                .flatMap(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    if (authentication.getPrincipal() instanceof Jwt){
                        Jwt jwt = (Jwt) authentication.getPrincipal();
                        String userId = jwt.getClaimAsString("sub");
                        return Mono.just(userId);
                    }
                    System.out.println("failed to get user id");
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return getUserId().flatMap(userId -> {
            if (userId != null){
                ServerHttpRequest modifiedRequest = exchange.getRequest()
                        .mutate()
                        .header("User-Id-Personalized", userId)
                        .build();
                ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                return chain.filter(modifiedExchange);
            }
            return chain.filter(exchange);
        }).switchIfEmpty(chain.filter(exchange)) ;
    }


}
