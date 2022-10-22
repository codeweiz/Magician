package cn.microboat.controller;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.LoginUser;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/login")
@Api(value = "LoginController", tags = "LoginController")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation(value = "register", notes = "注册")
    @PostMapping("/register")
    public Return<UserVo> register(@ApiParam @RequestBody UserDto userDto) {
        return loginService.register(userDto);
    }

    @ApiOperation(value = "login", notes = "登陆")
    @PostMapping("/login")
    public Return<LoginUser> login(@ApiParam @RequestBody UserDto userDto) {
        return loginService.login(userDto);
    }

    @ApiOperation(value = "resetPassword", notes = "重制密码")
    @PostMapping("/resetPassword")
    public Return<UserVo> resetPassword(@ApiParam @RequestBody UserDto userDto) {
        return loginService.resetPassword(userDto);
    }

}
