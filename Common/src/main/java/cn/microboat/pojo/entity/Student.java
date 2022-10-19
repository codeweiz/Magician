package cn.microboat.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhouwei
 */
@Data
public class Student {

    /**
     * 名称
     */
    private String name;

    /**
     * 出生日期
     */
    private LocalDateTime birthday;
}
