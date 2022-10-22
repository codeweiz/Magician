package cn.microboat.service.impl;

import cn.microboat.core.Return;
import cn.microboat.core.mapper.User2DtoMapper;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.pojo.entity.User;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.service.AuthService;
import cn.microboat.service.RemoteLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限认证服务接口实现类
 *
 * @author zhouwei
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final RemoteLoginService remoteLoginService;
    private final User2DtoMapper user2DtoMapper;

    @Autowired
    AuthServiceImpl(RemoteLoginService remoteLoginService, User2DtoMapper user2DtoMapper) {
        this.remoteLoginService = remoteLoginService;
        this.user2DtoMapper = user2DtoMapper;
    }


    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public Return<UserVo> register(String username, String password) {
        // 用户名和密码为空，错误
        User user = new User(username, password);
        return remoteLoginService.register(user2DtoMapper.entityToModel(user));
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return LoginUser
     */
    @Override
    public LoginUser login(String username, String password) {
        Return<LoginUser> login = remoteLoginService.login(user2DtoMapper.entityToModel(new User(username, password)));
        return login.getData();
    }

    /**
     * 登出
     *
     * @param username 用户名
     */
    @Override
    public void logout(String username) {

    }
}
