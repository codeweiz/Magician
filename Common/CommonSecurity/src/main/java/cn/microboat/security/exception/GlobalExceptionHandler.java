package cn.microboat.security.exception;

import cn.microboat.core.Return;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author zhouwei
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Return<?> handleException(Exception e) {
        return Return.fail("全局异常：" + e.getMessage());
    }
}
