package cn.microboat.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 消息队列消费者
 *
 * @author zhouwei
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "topic", groupId = "1")
    public void topicTest(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional.ofNullable(record.value()).ifPresent(message -> {
            log.info("topic_test 消费了：Topic：" + topic + "，Message：" + message);
            ack.acknowledge();
        });
    }
}
