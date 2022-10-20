package cn.microboat.controller;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.service.RemoteLoginService;
import cn.microboat.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/task")
@Api(value = "TaskController", tags = "TaskController")
public class TaskController {

    private final RemoteUserService remoteUserService;
    private final RemoteLoginService remoteLoginService;

    @Autowired
    TaskController(RemoteUserService remoteUserService, RemoteLoginService remoteLoginService) {
        this.remoteUserService = remoteUserService;
        this.remoteLoginService = remoteLoginService;
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取任务", notes = "获取任务", httpMethod = "GET")
    public String getTask() {
        return "task";
    }


    @GetMapping("/openfeign")
    @ApiOperation(value = "测试openfeign", notes = "测试openfeign", httpMethod = "GET")
    public Return<UserVo> testOpenfeign() {
        return remoteUserService.userInfo("admin");
    }

    @GetMapping("/openfeign2")
    @ApiOperation(value = "测试openfeign2", notes = "测试openfeign2", httpMethod = "GET")
    public Return<UserVo> testOpenfeign2() {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setPassword("admin");
        return remoteLoginService.login(userDto);
    }

}
