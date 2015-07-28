package com.wenin819.easyweb.filter.shiro;

import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.system.model.SysUser;
import com.wenin819.easyweb.system.service.SysLoginLogService;
import com.wenin819.easyweb.system.service.SysUserService;
import com.wenin819.easyweb.utils.LoginErrCntUtils;
import org.apache.shiro.authc.*;
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
    @Resource
    private SysLoginLogService sysLoginLogService;

    public static final String DEFAULT_ERROR_MSG_KEY_ATTRIBUTE_NAME = "message";
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
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String realRemoteAddr = WebUtils.getRealRemoteAddr(httpServletRequest);
        if (logger.isInfoEnabled()) {
            logger.info("用户[" + username + "]成功登陆，登陆IP为:" + realRemoteAddr);
        }
        sysUserService.updateUserLoginInfo(username);
        sysLoginLogService.saveLoginLog(username, realRemoteAddr, httpServletRequest.getHeader("user-agent"));
        SysUser sysUser = SecurityUtils.getCurrentUser();
        final String password = getPassword(request);
        final String hash = SecurityUtils.genHMacSha256(sysUser.getLoginName(), sysUser.getLoginName());
        ((HttpServletRequest) request).getSession().setAttribute("isDefaultPassword", password.equals(hash));
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        if (logger.isWarnEnabled()) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String realRemoteAddr = WebUtils.getRealRemoteAddr(httpServletRequest);
            logger.warn("用户[" + username + "]登陆失败，登陆IP为:" + realRemoteAddr);
        }
        return super.onLoginFailure(token, e, request, response);
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        super.setFailureAttribute(request, ae);
        String errMsg;
        if(ae instanceof CredentialsException) {
            errMsg = "用户名或密码错误，请重试。";
            String username = getUsername(request);
            LoginErrCntUtils.incrementLoginErrCnt(username);
        } else if(ae instanceof AccountException) {
            errMsg = "用户名或密码错误，请重试。";
        } else {
            errMsg = ae.getMessage();
        }
        request.setAttribute(DEFAULT_ERROR_MSG_KEY_ATTRIBUTE_NAME, errMsg);
    }
}
