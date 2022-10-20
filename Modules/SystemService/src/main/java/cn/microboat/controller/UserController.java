package cn.microboat.controller;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.service.LoginService;
import cn.microboat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = "UserController")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @ApiOperation(value = "register", notes = "注册")
    @PostMapping("/register")
    public Return<UserVo> register(@ApiParam @RequestBody UserDto userDto) {
        return loginService.register(userDto);
    }

    @ApiOperation(value = "login", notes = "登陆")
    @PostMapping("/login")
    public Return<UserVo> login(@ApiParam @RequestBody UserDto userDto) {
        return loginService.login(userDto);
    }

    @ApiOperation(value = "resetPassword", notes = "重制密码")
    @PostMapping("/resetPassword")
    public Return<UserVo> resetPassword(@ApiParam @RequestBody UserDto userDto) {
        return loginService.resetPassword(userDto);
    }

    @ApiOperation(value = "userInfo", notes = "获取用户信息")
    @GetMapping("/userInfo/{username}")
    public Return<UserVo> userInfo(@ApiParam @PathVariable(value = "username") String username) {
        return userService.getByUsername(username);
    }

    @ApiOperation(value = "list", notes = "获取所有用户信息")
    @GetMapping("/list")
    public Return<List<UserVo>> list() {
        return userService.userList();
    }
}
