package cn.microboat.mapper.converter;

import cn.microboat.core.constant.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 映射转换类
 *
 * @author zhouwei
 */
public class UserMapperConverter {

    /**
     * 性别整型转字符串型
     */
    public static String genderToString(Integer integer) {
        switch (integer) {
            case 0:
                return Constant.GENDER_FEMALE;
            case 1:
                return Constant.GENDER_MALE;
            default:
                return Constant.GENDER_UNKNOWN;
        }
    }


    /**
     * 性别字符串型转整型
     */
    public static Integer genderToInteger(String string) {
        if (Constant.GENDER_FEMALE.equals(string)) {
            return 0;
        }
        if (Constant.GENDER_MALE.equals(string)) {
            return 1;
        }
        return -1;
    }

    /**
     * LocalDateTime 转 String
     *
     * @param time LocalDateTime
     * @return String
     */
    public static String localDateTimeToString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(Constant.PATTERN_DATETIME));
    }

    /**
     * String 转 LocalDateTime
     *
     * @param string String
     * @return LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String string) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(Constant.PATTERN_DATETIME));
    }
}
