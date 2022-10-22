package cn.microboat.service;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.pojo.vo.UserVo;

/**
 * 权限认证服务接口
 *
 * @author zhouwei
 */
public interface AuthService {

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return Return<UserVo>
     */
    Return<UserVo> register(String username, String password);

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return LoginUser
     */
    LoginUser login(String username, String password);

    /**
     * 登出
     *
     * @param username 用户名
     */
    void logout(String username);
}
