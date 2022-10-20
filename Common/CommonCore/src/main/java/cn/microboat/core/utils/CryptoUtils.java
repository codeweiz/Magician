package cn.microboat.core.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.StandardCharsets;

/**
 * 加密工具类
 *
 * @author zhouwei
 */
public class CryptoUtils {

    /**
     * AES 下 KEY 的长度必须为 16 个字符
     */
    private static final String KEY = "MagicianMagician";

    /**
     * 使用 AES 构建
     */
    public static final SymmetricCrypto AES = new SymmetricCrypto(SymmetricAlgorithm.AES, SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), KEY.getBytes(StandardCharsets.UTF_8)).getEncoded());
}
