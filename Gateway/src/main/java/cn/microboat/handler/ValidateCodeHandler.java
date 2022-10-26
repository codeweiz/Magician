package cn.microboat.handler;

import cn.microboat.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 验证码 controller
 *
 * @author zhouwei
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {

    private final ValidateCodeService validateCodeService;

    @Autowired
    ValidateCodeHandler(ValidateCodeService validateCodeService) {
        this.validateCodeService = validateCodeService;
    }


    /**
     * Handle the given request.
     *
     * @param request the request to handle
     * @return the response
     */
    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {

        Map<String, Object> captcha = validateCodeService.createCaptcha();
        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(captcha));
    }
}
