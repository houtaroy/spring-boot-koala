package cn.houtaroy.springboot.koala.tools;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Houtaroy
 */
public class IpUtil {

    public static final String UNKNOWN = "unknown";

    /**
     * 获取请求的客户端IP地址
     *
     * @param request http请求
     * @return 请求的客户端IP地址
     */
    public static String getRemoteIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index >= 0) {
                ip = ip.substring(0, index);
            }
            return ip;
        } else {
            return request.getRemoteAddr();
        }
    }
}
