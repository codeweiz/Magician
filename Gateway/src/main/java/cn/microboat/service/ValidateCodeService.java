package cn.microboat.service;

import java.util.Map;

/**
 * 验证码处理接口
 *
 * @author zhouwei
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     *
     * @return Map
     */
    Map<String, Object> createCaptcha();

    /**
     * 校验验证码
     *
     * @param key   键
     * @param value 值
     * @return 是否校验通过
     */
    boolean checkCaptcha(String key, String value);
}
