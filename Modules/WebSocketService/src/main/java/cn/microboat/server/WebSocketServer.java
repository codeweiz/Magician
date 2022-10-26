package cn.microboat.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ws路径：ws://ip:port/ws
 *
 * @author zhouwei
 */
@Component
@Slf4j
@ServerEndpoint("/ws")
public class WebSocketServer {

    public static final String HASH_KEY = "magician:";

    public static final String CHANNEL = "channel:dev:";

    /**
     * 保存 websocket session
     * ConcurrentHashMap 保证在并发时也能正常使用
     */
    public static ConcurrentHashMap<String, Session> WEBSOCKET_MAP = null;

    /**
     * PostConstruct注释用于需要在依赖注入完成后执行的方法，以执行任何初始化。
     */
    @PostConstruct
    public void initMap() {
        if (WEBSOCKET_MAP == null) {
            WEBSOCKET_MAP = new ConcurrentHashMap<>();
        }
    }

    /**
     * 生成 map 的 key
     *
     * @param str session_id
     * @return key
     */
    private String getKey(String str) {
        return CHANNEL + HASH_KEY + str;
    }

    /**
     * 对应一个 websocket client 的开启
     *
     * @param session websocket 会话
     */
    @OnOpen
    public void open(Session session) {
        WEBSOCKET_MAP.put(getKey(session.getId()), session);
        log.info("【websocket消息】 有新连接：{}，总数为：{}", session.getId(), WEBSOCKET_MAP.entrySet().size());
    }

    /**
     * 对应一个 websocket client 的关闭
     *
     * @param session 会话
     */
    @OnClose
    public void close(Session session) {
        WEBSOCKET_MAP.remove(getKey(session.getId()));
        log.info("【websocket消息】 有连接断开：{}，总数为：{}", session.getId(), WEBSOCKET_MAP.entrySet().size());
    }

    /**
     * 对应一个 websocket client 发送的消息
     *
     * @param session 会话
     * @param msg     消息
     */
    @OnMessage
    public void message(Session session, String msg) {
        log.info("【websocket消息】从客户端：{}，收到消息：{}", session.getId(), msg);
    }

    /**
     * 对应一个 websocket client 的异常
     *
     * @param session   会话
     * @param throwable 异常
     */
    @OnError
    public void error(Session session, Throwable throwable) {
        log.error("【websocket消息】客户端：{}，发生异常：{}", session.getId(), throwable.getMessage());
    }

}
