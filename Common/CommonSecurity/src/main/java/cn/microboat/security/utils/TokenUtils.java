package cn.microboat.security.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.microboat.core.constant.CacheConstants;
import cn.microboat.core.constant.SecurityConstants;
import cn.microboat.core.constant.TokenConstants;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.utils.JwtUtils;
import cn.microboat.utils.IpUtils;
import cn.microboat.utils.RedisUtils;
import cn.microboat.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 管理 token 令牌的工具类
 *
 * @author zhouwei
 */
@Component
public class TokenUtils {

    private final RedisUtils redisUtils;

    @Autowired
    TokenUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 1000毫秒 == 1秒
     */
    protected static final long MILLIS_SECOND = 1000;

    /**
     * 60秒 == 1分钟
     */
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 缓存有效期
     */
    private final static long EXPIRE_TIME = CacheConstants.EXPIRATION;

    /**
     * 权限缓存前缀
     */
    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    /**
     * 缓存刷新时间：120 分钟
     */
    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;


    /**
     * 根据登陆用户创建令牌
     *
     * @param loginUser 登陆用户
     * @return Map<String, Object>
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        String token = IdUtil.fastSimpleUUID();
        Integer id = loginUser.getUser().getId();
        String username = loginUser.getUser().getUsername();
        loginUser.setToken(token);
        loginUser.setUserId(id);
        loginUser.setUsername(username);
        loginUser.setIpAddress(IpUtils.getIpAddress(ServletUtils.getRequest()));

        // 刷新令牌
        refreshToken(loginUser);

        // JWT 存储信息
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, id);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, username);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", EXPIRE_TIME);
        return rspMap;
    }

    /**
     * 获取登陆用户信息
     *
     * @return 登陆用户信息
     */
    public LoginUser getLoginUser() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getLoginUser(request);
    }

    /**
     * 从 request 中获取登陆用户信息
     *
     * @param request HttpServletRequest
     * @return 登陆用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        return getLoginUser(token);
    }

    /**
     * 从缓存中获取登陆用户信息
     *
     * @param token 令牌
     * @return 登陆用户信息
     */
    public LoginUser getLoginUser(String token) {
        String userKey = JwtUtils.getUserKey(token);
        return redisUtils.getCacheObject(getTokenKey(userKey));
    }

    /**
     * 设置用户身份信息
     *
     * @param loginUser 登陆用户
     */
    public void setLoginUser(LoginUser loginUser) {
        if (ObjectUtil.isNotEmpty(loginUser)) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除缓存的用户身份信息
     *
     * @param token 令牌
     */
    public void delLoginUser(String token) {
        if (StrUtil.isNotEmpty(token)) {
            String userKey = JwtUtils.getUserKey(token);
            redisUtils.deleteObject(getTokenKey(userKey));
        }
    }

    /**
     * 验证令牌有效期，相差不足 120 分钟，自动刷新缓存
     *
     * @param loginUser 登陆用户
     */
    public void verifyToken(LoginUser loginUser) {
        LocalDateTime expireTime = loginUser.getExpireTime();
        LocalDateTime currentTime = LocalDateTime.now();
        if (expireTime.isBefore(currentTime.plusNanos(MILLIS_MINUTE_TEN))) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登陆用户
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(LocalDateTime.now());
        loginUser.setExpireTime(loginUser.getLoginTime().plusMinutes(EXPIRE_TIME));
        String tokenKey = getTokenKey(loginUser.getToken());
        redisUtils.setCacheObject(tokenKey, loginUser, EXPIRE_TIME, TimeUnit.MINUTES);
    }

    /**
     * 检查 token 是否有效，缓存中是否有该令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public boolean checkTokenExists(String token) {
        if (StrUtil.isBlankIfStr(token)) {
            return false;
        }
        String userKey = JwtUtils.getUserKey(token);
        Object cacheObject = redisUtils.getCacheObject(getTokenKey(userKey));
        return ObjectUtil.isNotEmpty(cacheObject);
    }

    /**
     * token 加前缀，为了缓存中分组
     *
     * @param userKey 令牌
     * @return TokenKey
     */
    private String getTokenKey(String userKey) {
        return ACCESS_TOKEN + userKey;
    }
}
