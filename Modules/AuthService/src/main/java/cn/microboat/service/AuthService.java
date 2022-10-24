package cn.microboat.service;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.vo.UserVo;

import java.util.Map;

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
    Return<Map<String, Object>> login(String username, String password);

    /**
     * 登出
     *
     * @return 是否登出成功
     */
    boolean logout();

    /**
     * 刷新
     *
     * @return 是否成功
     */
    boolean refresh();
}
