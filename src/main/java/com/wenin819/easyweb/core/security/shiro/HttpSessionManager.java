package com.wenin819.easyweb.core.security.shiro;

import com.wenin819.easyweb.config.SessionConfigEnum;
import com.wenin819.easyweb.core.util.WebUtils;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Http Session管理类，增加一些属性的初始化
 * Created by wenin819@gmail.com on 2014-09-25.
 */
public class HttpSessionManager extends ServletContainerSessionManager {

    @Override
    protected Session createSession(SessionContext sessionContext) throws AuthorizationException {
        final Session session = super.createSession(sessionContext);
        HttpServletRequest request = org.apache.shiro.web.util.WebUtils.getHttpRequest(sessionContext);
        session.setAttribute(SessionConfigEnum.REAL_REMOTE_ADDR.name(),
                WebUtils.getRealRemoteAddr(request));
        return session;
    }
}
