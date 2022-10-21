package cn.microboat.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author zhouwei
 */
@Configuration
@EnableCaching
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @SuppressWarnings(value = { "unchecked" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 设置连接
        template.setConnectionFactory(connectionFactory);
        // 设置 FastJson2JsonRedisSerializer 序列化 Object
        FastJson2JsonRedisSerializer<Object> serializer = new FastJson2JsonRedisSerializer<>(Object.class);

        // 使用 StringRedisSerializer 来序列化和反序列化 redis 的 key 值
        template.setKeySerializer(new StringRedisSerializer());
        // 使用 FastJson2JsonRedisSerializer 来序列化和反序列化 redis 的 value 值
        template.setValueSerializer(serializer);

        // Hash 也使用 StringRedisSerializer 来序列化和反序列化 redis 的 key 值
        template.setHashKeySerializer(new StringRedisSerializer());
        // 使用 FastJson2JsonRedisSerializer 来序列化和反序列化 redis 的 value 值
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}
