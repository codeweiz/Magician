package cn.microboat.service;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.LoginBody;
import cn.microboat.core.pojo.dto.RegisterBody;
import cn.microboat.core.pojo.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限认证服务接口
 *
 * @author zhouwei
 */
public interface AuthService {

    /**
     * 注册
     *
     * @param registerBody 用户注册对象
     * @return Return<UserVo>
     */
    Return<UserVo> register(RegisterBody registerBody);

    /**
     * 登陆
     *
     * @param loginBody 用户登录对象
     * @return Return<UserVo>
     */
    Return<UserVo> login(LoginBody loginBody);

    /**
     * 登出
     *
     * @param request HttpServletRequest
     * @return Return<UserVo>
     */
    Return<UserVo> logout(HttpServletRequest request);

    /**
     * 刷新
     *
     * @param request HttpServletRequest
     * @return Return<UserVo>
     */
    Return<UserVo> refresh(HttpServletRequest request);

    /**
     * 重设密码
     *
     * @param loginBody 用户登录对象
     * @return Return<UserVo>
     */
    Return<UserVo> resetPassword(LoginBody loginBody);
}
