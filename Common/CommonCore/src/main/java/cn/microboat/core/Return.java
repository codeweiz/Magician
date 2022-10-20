package cn.microboat.core;

/**
 * 公共返回类
 *
 * @author zhouwei
 */
public class Return<T> {

    /**
     * 是否成功
     * true 成功
     * false 失败
     */
    private Boolean success;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 失败信息
     */
    private String error;

    /**
     * 无参构造
     */
    public Return() {
    }

    /**
     * 有参构造
     */
    public Return(Boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    /**
     * 成功
     */
    public static <T> Return<T> succeed(T t) {
        return new Return<>(true, t, null);
    }

    /**
     * 失败
     */
    public static <T> Return<T> fail(String error) {
        return new Return<>(false, null, error);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
