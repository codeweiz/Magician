package cn.microboat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.microboat.service.ValidateCodeService;
import cn.microboat.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhouwei
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    private final Producer captchaProducer;
    private final Producer captchaProducerMath;
    private final RedisUtils redisUtils;

    @Autowired
    ValidateCodeServiceImpl(@Qualifier("captchaProducer") Producer captchaProducer, @Qualifier("captchaProducerMath") Producer captchaProducerMath, RedisUtils redisUtils) {
        this.captchaProducer = captchaProducer;
        this.captchaProducerMath = captchaProducerMath;
        this.redisUtils = redisUtils;
    }

    /**
     * 生成验证码
     *
     * @return Map
     */
    @Override
    public Map<String, Object> createCaptcha() {
        return null;
    }

    /**
     * 校验验证码
     *
     * @param key   键
     * @param value 值
     * @return 是否校验通过
     */
    @Override
    public boolean checkCaptcha(String key, String value) {
        if (StrUtil.isEmptyIfStr(key)) {
            return false;
        }
        if (StrUtil.isEmptyIfStr(value)) {
            return false;
        }
        return true;
    }
}
