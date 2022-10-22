package cn.microboat.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.microboat.core.Return;
import cn.microboat.core.constant.SecurityConstants;
import cn.microboat.core.pojo.dto.LoginBody;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.pojo.dto.RegisterBody;
import cn.microboat.core.utils.JwtUtils;
import cn.microboat.security.utils.TokenUtils;
import cn.microboat.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/auth")
@Api(value = "AuthController", tags = "AuthController")
public class AuthController {

    private final AuthService authService;
    private final TokenUtils tokenUtils;

    AuthController(AuthService authService, TokenUtils tokenUtils) {
        this.authService = authService;
        this.tokenUtils = tokenUtils;
    }

    /**
     * 注册
     *
     * @param registerBody 用户注册对象
     * @return Return
     */
    @ApiOperation(value = "register", notes = "register", httpMethod = "POST")
    @PostMapping("/register")
    public Return<?> register(@ApiParam @RequestBody RegisterBody registerBody) {
        return authService.register(registerBody.getUsername(), registerBody.getPassword());
    }

    /**
     * 登陆
     *
     * @param loginBody 用户登录对象
     * @return Return
     */
    @ApiOperation(value = "login", notes = "login", httpMethod = "POST")
    @PostMapping("/login")
    public Return<?> login(@ApiParam @RequestBody LoginBody loginBody) {
        LoginUser loginUser = authService.login(loginBody.getUsername(), loginBody.getPassword());
        if (ObjectUtil.isEmpty(loginUser)) {
            return Return.fail("loginUser is null");
        }
        return Return.succeed(tokenUtils.createToken(loginUser));
    }

    /**
     * 登出
     *
     * @param request HttpServletRequest
     * @return Return
     */
    @ApiOperation(value = "logout", notes = "logout", httpMethod = "POST")
    @PostMapping("/logout")
    public Return<?> logout(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (StrUtil.isNotBlank(token)) {
            String userName = JwtUtils.getUserName(token);
            authService.logout(userName);
        }
        return Return.succeed();
    }

    /**
     * 刷新
     *
     * @param request HttpServletRequest
     * @return Return
     */
    @ApiOperation(value = "refresh", notes = "refresh", httpMethod = "POST")
    @PostMapping("/refresh")
    public Return<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenUtils.getLoginUser(request);
        if (ObjectUtil.isNotNull(loginUser)) {
            tokenUtils.refreshToken(loginUser);
        }
        return Return.succeed();
    }
}
