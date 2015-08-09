package com.wenin819.easyweb.listener;

import com.wenin819.easyweb.core.utils.SpringContextUtils;
import com.wenin819.easyweb.system.service.SysLoginLogService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * 会话监听器
 * @author wenin819@gmail.com
 * @date 2015-08-09.
 */
public class SessionListener implements HttpSessionListener {

    private SysLoginLogService sysLoginLogService;

    public SysLoginLogService getSysLoginLogService() {
        if(null == sysLoginLogService) {
            sysLoginLogService = SpringContextUtils.getBean(SysLoginLogService.class);
        }
        return sysLoginLogService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        getSysLoginLogService().updateLogoutDate((String) session.getAttribute(SysLoginLogService.LOGIN_LOG_ID_KEY),
                new Date(session.getLastAccessedTime()));
    }
}
