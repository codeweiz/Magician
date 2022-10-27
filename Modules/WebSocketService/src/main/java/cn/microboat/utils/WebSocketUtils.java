package cn.microboat.utils;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;

import static cn.microboat.server.WebSocketServer.*;

/**
 * websocket 工具类
 *
 * @author zhouwei
 */
@Slf4j
public class WebSocketUtils {

    /**
     * 向一个 websocket client 发送消息
     *
     * @param key 键
     * @param msg 消息
     */
    public static void sendToOneMessage(String key, String msg) {
        Session session = WEBSOCKET_MAP.get(key);
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(msg);
        }
    }

    /**
     * 向多个 websocket client 发送消息
     *
     * @param keys 键数组
     * @param msg  消息
     */
    public static void sendToMoreMessage(String[] keys, String msg) {
        for (String key : keys) {
            Session session = WEBSOCKET_MAP.get(key);
            if (session != null && session.isOpen()) {
                session.getAsyncRemote().sendText(msg);
            }
        }
    }

    /**
     * 向所有 websocket client 发送消息
     *
     * @param msg 消息
     */
    public static void sendToAllMessage(String msg) {
        log.info("【websocket消息】广播消息：{}", msg);
        WEBSOCKET_MAP.values().forEach(session -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(msg);
            }
        });
    }
}
