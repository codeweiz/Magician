package cn.microboat.controller;

import cn.microboat.Return;
import cn.microboat.pojo.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = "UserController")
public class UserController {

    @ApiOperation(value = "login", notes = "登陆")
    @PostMapping("/login")
    public Return<String> login(@ApiParam @RequestBody UserDto userDto) {
        if ("admin".equals(userDto.getUsername()) && "admin".equals(userDto.getPassword())) {
            return Return.succeed(userDto.getUsername());
        }
        return Return.fail("不是 admin");
    }

    @ApiOperation(value = "register", notes = "注册")
    @PostMapping("/register")
    public Return<String> register(@ApiParam @RequestBody UserDto userDto) {
        if ("admin".equals(userDto.getUsername()) && "admin".equals(userDto.getPassword())) {
            return Return.succeed(userDto.getUsername());
        }
        return Return.fail("不是 admin");
    }

    @ApiOperation(value = "userInfo", notes = "获取用户信息")
    @GetMapping("/userInfo/{username}")
    public Return<UserDto> userInfo(@ApiParam @PathVariable(value = "username") String username) {
        if ("admin".equals(username)) {
            return Return.succeed(new UserDto("admin", "admin"));
        }
        return Return.fail("暂无用户：" + username + "的信息");
    }
}
