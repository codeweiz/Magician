package cn.microboat.controller;

import cn.microboat.Return;
import cn.microboat.pojo.dto.UserDto;
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
}
