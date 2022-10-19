package cn.microboat.pojo.dto;

/**
 * 用户 Dto
 *
 * @author zhouwei
 */
public class UserDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public UserDto() {
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
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
}
