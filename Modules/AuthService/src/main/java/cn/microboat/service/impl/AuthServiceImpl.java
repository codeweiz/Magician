package cn.microboat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.microboat.core.Return;
import cn.microboat.core.constant.SecurityConstants;
import cn.microboat.core.mapper.User2DtoMapper;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.pojo.entity.User;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.security.utils.TokenUtils;
import cn.microboat.service.AuthService;
import cn.microboat.service.RemoteLoginService;
import cn.microboat.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 权限认证服务接口实现类
 *
 * @author zhouwei
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final TokenUtils tokenUtils;
    private final RemoteLoginService remoteLoginService;
    private final User2DtoMapper user2DtoMapper;

    @Autowired
    AuthServiceImpl(TokenUtils tokenUtils, RemoteLoginService remoteLoginService, User2DtoMapper user2DtoMapper) {
        this.tokenUtils = tokenUtils;
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
    public Return<Map<String, Object>> login(String username, String password) {
        Return<LoginUser> loginUserReturn = remoteLoginService.login(user2DtoMapper.entityToModel(new User(username, password)));

        // 返回失败
        if (!loginUserReturn.getSuccess()) {
            return Return.fail(loginUserReturn.getError());
        }

        // 返回成功
        return Return.succeed(tokenUtils.createToken(loginUserReturn.getData()));
    }

    /**
     * 登出
     *
     * @return 是否登出成功
     */
    @Override
    public boolean logout() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (tokenUtils.checkTokenExists(token)) {
            tokenUtils.delLoginUser(token);
            return true;
        }
        return false;
    }

    /**
     * 刷新
     *
     * @return 是否成功
     */
    @Override
    public boolean refresh() {
        HttpServletRequest request = ServletUtils.getRequest();
        LoginUser loginUser = tokenUtils.getLoginUser(request);
        if (ObjectUtil.isNotNull(loginUser)) {
            tokenUtils.refreshToken(loginUser);
            return true;
        }
        return false;
    }
}
