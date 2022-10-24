package cn.microboat.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

/**
 * 验证码文本生成器
 *
 * @author zhouwei
 */
public class KaptchaTextCreator extends DefaultTextCreator {

    private static final String[] CNUMBERS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    public String getText() {
        Integer result;
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        StringBuilder stringBuilder = new StringBuilder();
        switch (random.nextInt(3)) {
            // 乘
            case 0:
                result = x * y;
                stringBuilder.append(CNUMBERS[x]);
                stringBuilder.append("*");
                stringBuilder.append(CNUMBERS[y]);
                break;
            // 除
            case 1:
                if ((x != 0) && (y % x == 0)) {
                    result = y / x;
                    stringBuilder.append(CNUMBERS[y]);
                    stringBuilder.append("/");
                    stringBuilder.append(CNUMBERS[x]);
                } else {
                    result = x + y;
                    stringBuilder.append(CNUMBERS[x]);
                    stringBuilder.append("+");
                    stringBuilder.append(CNUMBERS[y]);
                }
                break;
            case 2:
                if (x > y) {
                    result = x - y;
                    stringBuilder.append(CNUMBERS[x]);
                    stringBuilder.append("-");
                    stringBuilder.append(CNUMBERS[y]);
                } else {
                    result = y - x;
                    stringBuilder.append(CNUMBERS[y]);
                    stringBuilder.append("-");
                    stringBuilder.append(CNUMBERS[x]);
                }
                break;
            default:
                result = x + y;
                stringBuilder.append(CNUMBERS[x]);
                stringBuilder.append("+");
                stringBuilder.append(CNUMBERS[y]);
                break;
        }
        stringBuilder.append("=?@").append(result);
        return stringBuilder.toString();
    }
}
