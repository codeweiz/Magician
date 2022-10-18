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
@RequestMapping("/test")
@Api(value = "测试控制器", tags = "TestController")
public class TestController {

    @GetMapping("/test1")
    @ApiOperation(value = "test1", notes = "测试方法1", httpMethod = "GET")
    public String test1() {
        return "test1";
    }
}
