package cn.microboat.producer;

import cn.microboat.pojo.Message;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 消息队列生产者
 *
 * @author zhouwei
 */
@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @SuppressWarnings("all")
    @Autowired
    KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String msg) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(msg);
        message.setSendTime(LocalDateTime.now());
        log.info("send msg：" + JSON.toJSON(message) + "，by topic test3");
        kafkaTemplate.send("topic", JSON.toJSONString(message));
    }

}
