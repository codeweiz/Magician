package cn.microboat.service.impl;

import cn.microboat.service.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

import static cn.microboat.server.WebSocketServer.*;

/**
 * @author zhouwei
 */
@Service
@Slf4j
public class WebSocketServiceImpl implements WebsocketService {

    private String getKey(String str) {
        return CHANNEL + HASH_KEY + str;
    }

    /**
     * 向一个 websocket client 发送消息
     *
     * @param sessionId 会话 id
     * @param msg       消息
     */
    @Override
    public void sendToOneMessage(String sessionId, String msg) {
        Session session = WEBSOCKET_MAP.get(getKey(sessionId));
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(msg);
        }
    }

    /**
     * 向多个 websocket client 发送消息
     *
     * @param sessionIds 会话 id 数组
     * @param msg        消息
     */
    @Override
    public void sendToMoreMessage(String[] sessionIds, String msg) {
        for (String sessionId : sessionIds) {
            Session session = WEBSOCKET_MAP.get(getKey(sessionId));
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
    @Override
    public void sendToAllMessage(String msg) {
        log.info("【websocket消息】广播消息：{}", msg);
        WEBSOCKET_MAP.values().forEach(session -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(msg);
            }
        });
    }
}
