package cn.microboat.controller;

import cn.microboat.Return;
import cn.microboat.pojo.dto.UserDto;
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

    @Autowired
    TaskController(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取任务", notes = "获取任务", httpMethod = "GET")
    public String getTask() {
        return "task";
    }


    @GetMapping("/openfeign")
    @ApiOperation(value = "测试openfeign", notes = "测试openfeign", httpMethod = "GET")
    public Return<UserDto> testOpenfeign() {
        return remoteUserService.userInfo("admin");
    }

}
