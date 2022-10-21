package cn.microboat.utils;

import cn.hutool.core.util.StrUtil;
import cn.microboat.core.constant.HttpConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * IpUtils 查询 IP 的工具类
 *
 * @author zhouwei
 */
public class IpUtils {

    /**
     * 获取客户端IP
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (null == request) {
            return HttpConstants.IP_UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || HttpConstants.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || HttpConstants.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || HttpConstants.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || HttpConstants.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || HttpConstants.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
    }

    /**
     * 从多级反向代理中获得第一个非 unknown IP 地址
     *
     * @param ip ip地址
     * @return 第一个 非 unknown IP 地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(HttpConstants.IP_SPLITTER) > 0) {
            final String[] ips = ip.trim().split(HttpConstants.IP_SPLITTER);
            for (String subIp : ips) {
                if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 检测给定字符串是否为未知，多用于检测 HTTP 请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString) {
        return StrUtil.isBlankIfStr(checkString) || HttpConstants.IP_UNKNOWN.equalsIgnoreCase(checkString);
    }
}
