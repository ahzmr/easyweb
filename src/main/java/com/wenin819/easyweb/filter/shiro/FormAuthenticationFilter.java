package com.wenin819.easyweb.filter.shiro;

import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.system.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统登陆
 * @author wenin819@gmail.com
 * @date 2015-07-19.
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    @Resource
    private SysUserService sysUserService;

    private static final Logger logger = LoggerFactory.getLogger(FormAuthenticationFilter.class);

    @Override
    protected String getHost(ServletRequest request) {
        return WebUtils.getRealRemoteAddr((HttpServletRequest) request);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        return new UsernamePasswordToken(username, password, isRememberMe(request), getHost(request));
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        String username = getUsername(request);
        if (logger.isInfoEnabled()) {
            logger.info("用户[" + username + "]成功登陆，登陆IP为:" + WebUtils.getRealRemoteAddr());
        }
        sysUserService.updateUserLoginInfo(username);
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        if (logger.isWarnEnabled()) {
            logger.warn("用户[" + username + "]登陆失败，登陆IP为:" + WebUtils.getRealRemoteAddr());
        }
        return super.onLoginFailure(token, e, request, response);
    }
}
