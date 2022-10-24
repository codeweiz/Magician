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

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/auth")
@Api(value = "AuthController", tags = "AuthController")
public class AuthController {

    private final AuthService authService;

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
        return authService.login(loginBody.getUsername(), loginBody.getPassword());
    }

    /**
     * 登出
     *
     * @return Return
     */
    @ApiOperation(value = "logout", notes = "logout", httpMethod = "POST")
    @PostMapping("/logout")
    public Return<?> logout() {
        if (authService.logout()) {
            return Return.succeed();
        }
        return Return.fail("登出失败");
    }

    /**
     * 刷新
     *
     * @return Return
     */
    @ApiOperation(value = "refresh", notes = "refresh", httpMethod = "POST")
    @PostMapping("/refresh")
    public Return<?> refresh() {
        if (authService.refresh()) {
            return Return.succeed();
        }
        return Return.fail("刷新失败");
    }
}
