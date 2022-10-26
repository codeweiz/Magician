package cn.microboat.filter;

import cn.hutool.core.util.StrUtil;
import cn.microboat.config.properties.CaptchaProperties;
import cn.microboat.core.Return;
import cn.microboat.service.ValidateCodeService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 验证码过滤器
 *
 * @author zhouwei
 */
@Component
@Slf4j
public class ValidateCodeFilter extends AbstractGatewayFilterFactory<Object> {

    /**
     * 需要验证的路径
     */
    private final static String[] VALIDATE_URL = new String[]{"/api/auth/auth/login", "/api/auth/auth/register"};

    private final ValidateCodeService validateCodeService;

    private final CaptchaProperties captchaProperties;

    @Autowired
    ValidateCodeFilter(ValidateCodeService validateCodeService, CaptchaProperties captchaProperties) {
        this.validateCodeService = validateCodeService;
        this.captchaProperties = captchaProperties;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            //  非登陆、注册请求，不处理
            if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL)) {
                return chain.filter(exchange);
            }

            // 验证码如果配置为关闭，不处理
            if (!captchaProperties.getEnabled()) {
                return chain.filter(exchange);
            }

            try {
                String rspStr = resolveBodyFromRequest(request);
                JSONObject obj = JSON.parseObject(rspStr);
                validateCodeService.checkCaptcha(obj.getString("captchaCode"), obj.getString("uuid"));
                return chain.filter(exchange);
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                // 自定义返回
                // writeAndFlushWith 可以返回对象，但需要封装两层 Flux
                // writeWith 可以返回字符串，但需要封装一层 Flux
                return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(JSON.toJSONBytes(Return.fail(e.getMessage()))))));
            }
        };
    }

    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        // 获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }
}
