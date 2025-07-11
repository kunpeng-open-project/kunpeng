package com.kunpeng.framework.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CorsResponseHeaderFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();

            // 移除重复的Access-Control-Allow-Origin头，只保留第一个
            String allowOriginHeader = "Access-Control-Allow-Origin";
            if (response.getHeaders().containsKey(allowOriginHeader)) {
                java.util.List<String> values = response.getHeaders().get(allowOriginHeader);
                if (values != null && values.size() > 1) {
                    response.getHeaders().set(allowOriginHeader, values.get(0));
                }
            }

            // 同样处理其他可能重复的CORS头
            String[] corsHeaders = {
                    "Access-Control-Allow-Methods",
                    "Access-Control-Allow-Headers",
                    "Access-Control-Allow-Credentials"
            };

            for (String header : corsHeaders) {
                if (response.getHeaders().containsKey(header)) {
                    java.util.List<String> values = response.getHeaders().get(header);
                    if (values != null && values.size() > 1) {
                        response.getHeaders().set(header, values.get(0));
                    }
                }
            }
        }));
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}