package cn.microboat.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.microboat.config.properties.CaptchaProperties;
import cn.microboat.core.constant.CacheConstants;
import cn.microboat.core.constant.HttpStatus;
import cn.microboat.core.exception.CaptchaException;
import cn.microboat.service.ValidateCodeService;
import cn.microboat.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouwei
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    /**
     * 字符串验证码生成器
     */
    private final Producer captchaProducer;

    /**
     * 数学算式验证码生成器
     */
    private final Producer captchaProducerMath;

    /**
     * redis 缓存工具类
     */
    private final RedisUtils redisUtils;

    /**
     * 验证码配置
     */
    private final CaptchaProperties captchaProperties;

    @Autowired
    ValidateCodeServiceImpl(@Qualifier("captchaProducer") Producer captchaProducer, @Qualifier("captchaProducerMath") Producer captchaProducerMath, RedisUtils redisUtils, CaptchaProperties captchaProperties) {
        this.captchaProducer = captchaProducer;
        this.captchaProducerMath = captchaProducerMath;
        this.redisUtils = redisUtils;
        this.captchaProperties = captchaProperties;
    }

    /**
     * 生成验证码
     *
     * @return Map
     */
    @Override
    public Map<String, Object> createCaptcha() {

        Map<String, Object> result = new HashMap<>();
        boolean captchaEnabled = captchaProperties.getEnabled();
        result.put("code", HttpStatus.SUCCESS);
        result.put("captchaEnabled", captchaEnabled);

        // 如果未开启验证码，直接返回
        if (!captchaEnabled) {
            return result;
        }

        // 保存验证码信息
        String uuid = IdUtil.fastSimpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String capStr;
        String code = null;
        BufferedImage image = null;

        String captchaType = captchaProperties.getType();

        // 生成验证码
        switch (captchaType) {
            case "math":
                String captchaText = captchaProducerMath.createText();
                capStr = captchaText.substring(0, captchaText.lastIndexOf("@"));
                code = captchaText.substring(captchaText.lastIndexOf("@") + 1);
                image = captchaProducerMath.createImage(capStr);
                break;
            case "char":
                capStr = code = captchaProducer.createText();
                image = captchaProducer.createImage(capStr);
                break;
            default:
                break;
        }

        // 把 code 存入 redis，默认过期时间为 2 分钟
        redisUtils.setCacheObject(verifyKey, code, 2L, TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            assert image != null;
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            result.put("code", HttpStatus.ERROR);
            return result;
        }
        result.put("uuid", uuid);
        result.put("img", Base64.encode(os.toByteArray()));
        return result;
    }

    /**
     * 校验验证码
     *
     * @param code 键
     * @param uuid 值
     */
    @Override
    public void checkCaptcha(String code, String uuid) {
        if (StrUtil.isEmptyIfStr(code)) {
            throw new CaptchaException("captcha code is empty");
        }
        if (StrUtil.isEmptyIfStr(uuid)) {
            throw new CaptchaException("uuid is empty");
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisUtils.getCacheObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException("captcha is error");
        }
    }
}
