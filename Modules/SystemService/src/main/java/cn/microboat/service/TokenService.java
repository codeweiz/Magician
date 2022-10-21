package cn.microboat.service;

import cn.microboat.model.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 令牌服务
 *
 * @author zhouwei
 */
public interface TokenService {

    /**
     * 根据登陆用户创建令牌
     *
     * @param loginUser 登陆用户
     * @return Map<String, Object>
     */
    Map<String, Object> createToken(LoginUser loginUser);

    /**
     * 获取登陆用户信息
     *
     * @return 登陆用户信息
     */
    LoginUser getLoginUser();

    /**
     * 从 request 中获取登陆用户信息
     *
     * @param request HttpServletRequest
     * @return 登陆用户信息
     */
    LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 从令牌中获取登陆用户信息
     *
     * @param token 令牌
     * @return 登陆用户信息
     */
    LoginUser getLoginUser(String token);

    /**
     * 设置用户身份信息
     *
     * @param loginUser 登陆用户
     */
    void setLoginUser(LoginUser loginUser);

    /**
     * 删除缓存的用户身份信息
     *
     * @param token 令牌
     */
    void delLoginUser(String token);

    /**
     * 验证令牌有效期，相差不足 120 分钟，自动刷新缓存
     *
     * @param loginUser 登陆用户
     */
    void verifyToken(LoginUser loginUser);

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登陆用户
     */
    void refreshToken(LoginUser loginUser);
}
