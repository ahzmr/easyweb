package com.wenin819.easyweb.filter.shiro;

import com.wenin819.easyweb.system.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 退出拦截器
 * @author wenin819@gmail.com
 * @date 2015-08-09.
 */
@Service
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    @Resource
    private SysLoginLogService sysLoginLogService;

    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
        sysLoginLogService.updateLogoutDate((String) ((HttpServletRequest) request).getSession()
                .getAttribute(SysLoginLogService.LOGIN_LOG_ID_KEY), new Date());
        super.issueRedirect(request, response, redirectUrl);
    }
}
