package com.wenin819.easyweb.core.utils;

import com.wenin819.easyweb.config.SessionConfigEnum;

import com.wenin819.easyweb.utils.HttpContext;
import com.wenin819.easyweb.utils.HttpUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    public static final String TAOBAO_IP2ADDR_CACHE = "taobaoIp2AddrCache"; // 淘宝IP解析地址缓存
    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

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

    /**
     * 淘宝通过IP查地址接口会话
     */
    private static final HttpContext TAOBAO_IP_QUERY =
            new HttpContext("http://ip.taobao.com/service/getIpInfo.php");

    /**
     * 通过IP地址查地址
     * @param ipAddr IP地址
     * @return
     */
    public static String getLocationNameByIp(String ipAddr) {
        if(StringUtils.isBlank(ipAddr)) {
            return null;
        }
        String addr = CacheUtils.get(TAOBAO_IP2ADDR_CACHE, ipAddr, null);
        if(null != addr) {
            return addr;
        }
        synchronized (TAOBAO_IP2ADDR_CACHE) {
            addr = CacheUtils.get(TAOBAO_IP2ADDR_CACHE, ipAddr, null);
            if(null != addr) {
                return addr;
            }
            String rsStr = HttpUtils.httpPost(TAOBAO_IP_QUERY.addParam("ip", ipAddr));
            if (null == rsStr || rsStr.length() == 0 || !rsStr.startsWith("{")) {
                logger.error("请求淘宝IP地址查询接口失败");
                return null;
            }
            Map<Object, Object> data = JsonUtils.jsonToMap(rsStr);
            if (null != (data = (Map<Object, Object>) data.get("data"))) {
                addr = StringUtils.join(data.get("region"), data.get("city"));
            } else {
                addr = "";
            }
            CacheUtils.put(TAOBAO_IP2ADDR_CACHE, ipAddr, addr);
            return addr;
        }
    }

}
