package cn.microboat.core.pojo.dto;

/**
 * 用户登录对象
 *
 * @author zhouwei
 */
public class LoginBody {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public LoginBody() {
    }

    public LoginBody(String username, String password) {
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
