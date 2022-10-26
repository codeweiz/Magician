package cn.microboat.service;

/**
 * websocket 服务接口
 *
 * @author zhouwei
 */
public interface WebsocketService {

    /**
     * 向一个 websocket client 发送消息
     *
     * @param sessionId 会话 id
     * @param msg       消息
     */
    void sendToOneMessage(String sessionId, String msg);

    /**
     * 向多个 websocket client 发送消息
     *
     * @param sessionIds 会话 id 数组
     * @param msg        消息
     */
    void sendToMoreMessage(String[] sessionIds, String msg);

    /**
     * 向所有 websocket client 发送消息
     *
     * @param msg 消息
     */
    void sendToAllMessage(String msg);
}
