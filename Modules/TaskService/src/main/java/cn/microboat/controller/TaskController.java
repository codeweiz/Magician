package cn.microboat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/task")
@Api(value = "task", tags = "任务控制器")
public class TaskController {

    @GetMapping("/get")
    @ApiOperation(value = "获取任务", notes = "获取任务", httpMethod = "GET")
    public String getTask() {
        return "task";
    }

}
