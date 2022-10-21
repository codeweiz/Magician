package cn.microboat.service.impl;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.LoginBody;
import cn.microboat.core.pojo.dto.RegisterBody;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.service.AuthService;
import cn.microboat.service.RemoteLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限认证服务接口实现类
 *
 * @author zhouwei
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final RemoteLoginService remoteLoginService;

    @Autowired
    AuthServiceImpl(RemoteLoginService remoteLoginService) {
        this.remoteLoginService = remoteLoginService;
    }

    /**
     * 注册
     *
     * @param registerBody 用户注册对象
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> register(RegisterBody registerBody) {
        return remoteLoginService.register(new UserDto(registerBody.getUsername(), registerBody.getPassword()));
    }

    /**
     * 登陆
     *
     * @param loginBody 用户登录对象
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> login(LoginBody loginBody) {
        return remoteLoginService.login(new UserDto(loginBody.getUsername(), loginBody.getPassword()));
    }

    /**
     * 登出
     *
     * @param request HttpServletRequest
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> logout(HttpServletRequest request) {
        return null;
    }

    /**
     * 刷新
     *
     * @param request HttpServletRequest
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> refresh(HttpServletRequest request) {
        return null;
    }

    /**
     * 重设密码
     *
     * @param loginBody 用户登录对象
     * @return Return<UserVo>
     */
    @Override
    public Return<UserVo> resetPassword(LoginBody loginBody) {
        return remoteLoginService.resetPassword(new UserDto(loginBody.getUsername(), loginBody.getPassword()));
    }
}
