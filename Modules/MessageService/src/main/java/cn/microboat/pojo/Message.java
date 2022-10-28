package cn.microboat.pojo;

import java.time.LocalDateTime;

/**
 * 消息体
 *
 * @author zhouwei
 */
public class Message {

    /**
     * 消息 id
     */
    private Long id;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
