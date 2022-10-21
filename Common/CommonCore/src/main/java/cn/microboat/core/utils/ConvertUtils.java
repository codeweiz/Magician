package cn.microboat.core.utils;

/**
 * 类转换工具类
 *
 * @author zhouwei
 */
public class ConvertUtils {

    /**
     * Object 转 String
     *
     * @param value        待转换的 Object
     * @param defaultValue 默认字符串
     * @return String
     */
    public static String toStr(Object value, String defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }
}
