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
     * @param code 键
     * @param uuid 值
     */
    void checkCaptcha(String code, String uuid);
}
