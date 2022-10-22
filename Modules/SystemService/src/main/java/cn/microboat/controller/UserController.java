package cn.microboat.controller;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.vo.UserVo;
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

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "userInfo", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/userInfo/{username}")
    public Return<UserVo> userInfo(@ApiParam @PathVariable(value = "username") String username) {
        return userService.getByUsername(username);
    }

    @ApiOperation(value = "list", notes = "获取所有用户信息", httpMethod = "GET")
    @GetMapping("/list")
    public Return<List<UserVo>> list() {
        return userService.userList();
    }

    @ApiOperation(value = "deleteUsersByIds", notes = "根据id列表删除用户列表", httpMethod = "DELETE")
    @DeleteMapping
    public Return<?> deleteUsersByIds(@RequestBody List<Integer> ids) {
        return userService.batchDelete(ids);
    }
}
