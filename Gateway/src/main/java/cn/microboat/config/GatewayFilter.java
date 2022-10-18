package cn.microboat.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关过滤器
 *
 * @author zhouwei
 */
@Component
public class GatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        List<String> accessControlAllowHeaders = new ArrayList<>();
        accessControlAllowHeaders.add("origin");
        accessControlAllowHeaders.add("x-requested-with");
        accessControlAllowHeaders.add("content-type");
        accessControlAllowHeaders.add("accept");
        response.getHeaders().setAccessControlAllowHeaders(accessControlAllowHeaders);
        response.getHeaders().setAccessControlMaxAge(3600L);
        List<HttpMethod> httpMethodList = new ArrayList<>();
        httpMethodList.add(HttpMethod.GET);
        httpMethodList.add(HttpMethod.POST);
        httpMethodList.add(HttpMethod.PUT);
        httpMethodList.add(HttpMethod.DELETE);
        httpMethodList.add(HttpMethod.OPTIONS);
        httpMethodList.add(HttpMethod.PATCH);
        response.getHeaders().setAccessControlAllowMethods(httpMethodList);
        response.getHeaders().setAccessControlAllowOrigin(exchange.getRequest().getHeaders().getOrigin());
        List<String> accessControlExposeHeaders = new ArrayList<>();
        accessControlExposeHeaders.add("location");
        response.getHeaders().setAccessControlExposeHeaders(accessControlExposeHeaders);
        response.getHeaders().setAccessControlAllowCredentials(true);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 最先执行
        return Integer.MIN_VALUE;
    }
}
