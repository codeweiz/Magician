package cn.microboat.core.pojo.entity;

import java.time.LocalDateTime;

/**
 * @author zhouwei
 */
public class User extends BasicEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别：<br/>
     * 0 女<br/>
     * 1 男
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDateTime birthday;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 电子邮箱
     */
    private String email;

    public User() {
    }

    /**
     * 创建包含用户名和密码的 User 对象
     *
     * @param username 用户名
     * @param password 密码
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Integer gender, LocalDateTime birthday, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
