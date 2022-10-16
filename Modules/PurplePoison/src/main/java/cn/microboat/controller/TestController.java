package cn.microboat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouwei
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }
}
