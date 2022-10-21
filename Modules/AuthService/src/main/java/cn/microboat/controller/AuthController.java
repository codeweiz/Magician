package cn.microboat.controller;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.LoginBody;
import cn.microboat.core.pojo.dto.RegisterBody;
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
//    private final TokenService tokenService;

    AuthController(AuthService authService) {
        this.authService = authService;
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
        return authService.register(registerBody);
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
        return authService.login(loginBody);
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
        return authService.logout(request);
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
        return authService.refresh(request);
    }

    /**
     * 重设密码
     *
     * @param loginBody LoginBody
     * @return Return
     */
    @ApiOperation(value = "resetPassword", notes = "resetPassword", httpMethod = "POST")
    @PostMapping("/resetPassword")
    public Return<?> resetPassword(@ApiParam @RequestBody LoginBody loginBody) {
        return authService.resetPassword(loginBody);
    }
}
