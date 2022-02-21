package com.af.gateway.filter;

import com.af.common.constant.HttpConstants;
import com.af.common.constant.TokenConstants;
import com.af.common.utils.JwtUtil;
import com.af.common.utils.UrlUtil;
import com.af.gateway.properties.FilterIgnoreUrlProperties;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 网关统一鉴权
 *
 * @author Tanglinfeng
 * @date 2022/2/14 15:27
 */
@Configuration
public class SecurityFilter implements GlobalFilter, Ordered {

    private final FilterIgnoreUrlProperties filterIgnoreUrlProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestUrl = exchange.getRequest().getURI().getPath();

        // 白名单放行
        if (isIgnored(requestUrl)) {
            return chain.filter(exchange);
        }

        ServerHttpResponse response = exchange.getResponse();
        // 获取token
        String token = exchange.getRequest().getHeaders().getFirst(TokenConstants.TOKEN_HEADER);

        // 未携带token或已过期
        if (StringUtils.isEmpty(token) || JwtUtil.isExpired(token)) {
            return writeWebFluxResponse(response, HttpConstants.CONTENT_TYPE, HttpStatus.UNAUTHORIZED, "token为空或已过期");
        }


        return chain.filter(exchange);
    }

    private boolean isIgnored(String path) {
        return filterIgnoreUrlProperties.getUrls().stream()
                .anyMatch(url -> UrlUtil.matching(url, path));
    }


    /**
     * WebFlux模型响应
     * @param response
     * @param contentType
     * @param status
     * @param value
     * @return
     */
    private Mono<Void> writeWebFluxResponse(ServerHttpResponse response, String contentType, HttpStatus status, Object value) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        Map<String, Object> responseMap = new HashMap<String, Object>() {{
            put("code", status.value());
            put("msg", status.name());
            put("data", value);
        }};

        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(responseMap).getBytes());

        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public SecurityFilter(final FilterIgnoreUrlProperties filterIgnoreUrlProperties) {
        this.filterIgnoreUrlProperties = filterIgnoreUrlProperties;
    }
}
