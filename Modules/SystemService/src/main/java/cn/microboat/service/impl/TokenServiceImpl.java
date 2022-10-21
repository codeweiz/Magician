package cn.microboat.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.microboat.core.constant.CacheConstants;
import cn.microboat.core.constant.SecurityConstants;
import cn.microboat.core.utils.JwtUtils;
import cn.microboat.model.LoginUser;
import cn.microboat.service.RedisService;
import cn.microboat.service.TokenService;
import cn.microboat.utils.IpUtils;
import cn.microboat.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouwei
 */
@Service
public class TokenServiceImpl implements TokenService {

    private final RedisService redisService;

    @Autowired
    TokenServiceImpl(RedisService redisService) {
        this.redisService = redisService;
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
    @Override
    public Map<String, Object> createToken(LoginUser loginUser) {
        String token = IdUtil.fastUUID();
        Integer id = loginUser.getUser().getId();
        String username = loginUser.getUser().getUsername();
        loginUser.setToken(token);
        loginUser.setUserId(id);
        loginUser.setUsername(username);
        loginUser.setIpAddress(IpUtils.getIpAddress(ServletUtils.getRequest()));
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
    @Override
    public LoginUser getLoginUser() {
        return null;
    }

    /**
     * 从 request 中获取登陆用户信息
     *
     * @param request HttpServletRequest
     * @return 登陆用户信息
     */
    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        return null;
    }

    /**
     * 从令牌中获取登陆用户信息
     *
     * @param token 令牌
     * @return 登陆用户信息
     */
    @Override
    public LoginUser getLoginUser(String token) {
        return null;
    }

    /**
     * 设置用户身份信息
     *
     * @param loginUser 登陆用户
     */
    @Override
    public void setLoginUser(LoginUser loginUser) {

    }

    /**
     * 删除缓存的用户身份信息
     *
     * @param token 令牌
     */
    @Override
    public void delLoginUser(String token) {

    }

    /**
     * 验证令牌有效期，相差不足 120 分钟，自动刷新缓存
     *
     * @param loginUser 登陆用户
     */
    @Override
    public void verifyToken(LoginUser loginUser) {

    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登陆用户
     */
    @Override
    public void refreshToken(LoginUser loginUser) {

    }
}
