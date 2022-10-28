package cn.microboat.controller;

import cn.microboat.producer.KafkaProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka 消息队列 controller
 *
 * @author zhouwei
 */
@Api(value = "KafkaController", tags = "KafkaController")
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @Autowired
    KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    @ApiOperation(value = "发送消息", notes = "发送消息", httpMethod = "GET")
    @GetMapping("/send/{msg}")
    public void send(@ApiParam @PathVariable("msg") String msg) {
        kafkaProducer.send(msg);
    }
}
