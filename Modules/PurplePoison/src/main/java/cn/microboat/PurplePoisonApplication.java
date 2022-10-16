package cn.microboat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhouwei
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PurplePoisonApplication {
    public static void main(String[] args) {
        SpringApplication.run(PurplePoisonApplication.class, args);
    }
}
