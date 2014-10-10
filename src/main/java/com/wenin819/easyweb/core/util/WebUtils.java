package com.wenin819.easyweb.core.util;

import com.wenin819.easyweb.config.SessionConfigEnum;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import javax.servlet.http.HttpServletRequest;

/**
 * Web 操作工具类
 * Created by wenin819@gmail.com on 2014-09-25.
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

    public static final String ENTRY = "entry"; // 实体
    public static final String LIST = "list";   // 列表
    public static final String PAGE = "page";   // 分页
    public static final String MSG = "message"; // 消息
    public static final String MSG_PAGE = "common/message"; // 消息页面

    /**
     * 客户端地址请求头，分先后顺序
     */
    private static final String[] REAL_REMOTE_ADDR_NAMES = new String[]{
            "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP"
    };

    /**
     * 得到准确的客户端地址，跳过反向代理
     * @param request
     * @return
     */
    public static String getRealRemoteAddr(HttpServletRequest request) {
        if(null == request) {
            return null;
        }
        String ip;
        for (String realRemoteAddrName : REAL_REMOTE_ADDR_NAMES) {
            ip = request.getHeader(realRemoteAddrName);
            if(!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                final int idx = ip.indexOf(',');
                if(-1 < idx) {
                    ip = ip.substring(0, idx);
                }
                return ip.trim();
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 得到当前准确的客户端地址，跳过反向代理
     * @return
     */
    public static String getRealRemoteAddr() {
        return getSessionAttr(SessionConfigEnum.REAL_REMOTE_ADDR.name());
    }

    /**
     * 得到当前Session的属性值
     * @param key 属性键
     * @param <T> 返回类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttr(Object key) {
        final Session session = SecurityUtils.getSubject().getSession();
        return null == session ? null : (T) session.getAttribute(key);
    }

}
