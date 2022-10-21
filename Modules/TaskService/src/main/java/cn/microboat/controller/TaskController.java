package cn.microboat.controller;

import cn.microboat.core.Return;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.core.pojo.vo.UserVo;
import cn.microboat.domain.Task;
import cn.microboat.service.RemoteLoginService;
import cn.microboat.service.RemoteUserService;
import cn.microboat.service.TaskService;
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
@RequestMapping("/task")
@Api(value = "TaskController", tags = "TaskController")
public class TaskController {

    private final TaskService taskService;
    private final RemoteUserService remoteUserService;
    private final RemoteLoginService remoteLoginService;

    @Autowired
    TaskController(TaskService taskService, RemoteUserService remoteUserService, RemoteLoginService remoteLoginService) {
        this.taskService = taskService;
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

    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 获取任务信息", notes = "根据 id 获取任务信息", httpMethod = "GET")
    public Return<Task> getTaskInfo(@PathVariable Integer id) {
        return Return.succeed(taskService.selectTaskById(id));
    }

    @PostMapping("/list")
    @ApiOperation(value = "根据条件获取所有任务信息", notes = "根据条件获取所有任务信息", httpMethod = "POST")
    public Return<List<Task>> getTaskList(@ApiParam @RequestBody Task task) {
        return Return.succeed(taskService.selectTaskList(task));
    }

    @PostMapping
    @ApiOperation(value = "新增任务", notes = "新增任务", httpMethod = "POST")
    public Return<Integer> addTask(@RequestBody Task task) {
        return Return.succeed(taskService.insertTask(task));
    }

    @PutMapping
    @ApiOperation(value = "编辑任务", notes = "编辑任务", httpMethod = "PUT")
    public Return<Integer> editTask(@RequestBody Task task) {
        return Return.succeed(taskService.updateTask(task));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据 id 删除任务", notes = "根据 id 删除任务", httpMethod = "DELETE")
    public Return<Integer> deleteTaskById(@PathVariable Integer id) {
        return Return.succeed(taskService.deleteTaskById(id));
    }

}
