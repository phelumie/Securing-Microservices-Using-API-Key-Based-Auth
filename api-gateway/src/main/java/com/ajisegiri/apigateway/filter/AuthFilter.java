package com.ajisegiri.apigateway.filter;

import com.ajisegiri.Common.repo.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
    private final AuthRepository authRepository;

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        var apiKeyHeader = exchange.getRequest().getHeaders().get("Authorization");
        log.info("api key {} ", apiKeyHeader);
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String routeId = route != null ? route.getId() : "";
        if (!excludeUrls(exchange)) {
            if (CollectionUtils.isEmpty(apiKeyHeader)||apiKeyHeader.get(0).length()==0)
                return unAuthorizedException(exchange, "Unauthorized access,please provide your api key", HttpStatus.UNAUTHORIZED);

            var user = authRepository.findById(apiKeyHeader.get(0));

            if (!user.isPresent())
                return unAuthorizedException(exchange, "Please provide a valid api key", HttpStatus.UNAUTHORIZED);

            if (!user.get().isAccountActivated()){
                log.error("User with api key {} has not been activated ", user.get().getKey());
                return unAuthorizedException(exchange, "Please activate your account before using the api key", HttpStatus.UNAUTHORIZED);
            }
            if (!user.get().getServices().contains(routeId))
                return unAuthorizedException(exchange, "You are unauthorized to use this service", HttpStatus.UNAUTHORIZED);

        }
        return chain.filter(exchange);
    }

    private static Mono<Void> unAuthorizedException(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = String.format("{\"error\":\"%s\"}", message).getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    private boolean excludeUrls(ServerWebExchange exchange){
        var path = exchange.getRequest().getPath().value();
        return path.equals("/api/v1/api-key");
    }


}