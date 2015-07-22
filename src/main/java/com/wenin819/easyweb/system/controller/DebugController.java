package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.core.web.BaseController;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wenin819@gmail.com
 * @date 2015-07-22.
 */
@RequestMapping("/debug/")
@Controller
public class DebugController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DebugController.class);

    @RequiresRoles(SecurityUtils.SUPER_ADMIN_NAME)
    @RequestMapping("requestInfo")
    @ResponseBody
    public Map<Object, Object> requestInfo(HttpServletRequest request) {
        Map<Object, Object> rst = new HashMap<Object, Object>();

        Map<String, Object> attrs = new HashMap<String, Object>();
        Enumeration attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = (String) attributeNames.nextElement();
            attrs.put(name, ObjectUtils.toString(request.getAttribute(name)));
        }
        rst.put("Attributes", attrs);

        rst.put("CharacterEncoding", request.getCharacterEncoding());
        rst.put("ContentLength", request.getContentLength());
        rst.put("ContentType", request.getContentType());

        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = (String) parameterNames.nextElement();
            params.put(paramName, request.getParameterValues(paramName));
        }
        rst.put("ParameterValues", params);

        rst.put("ParameterMap", request.getParameterMap());
        rst.put("Protocol", request.getProtocol());
        rst.put("Scheme", request.getScheme());
        rst.put("ServerName", request.getServerName());
        rst.put("ServerPort", request.getServerPort());
        rst.put("RemoteAddr", request.getRemoteAddr());
        rst.put("RemoteHost", request.getRemoteHost());
        rst.put("Locale", request.getLocale().toString());
        rst.put("Locales", request.getLocales().toString());
        rst.put("isSecure", request.isSecure());
        rst.put("RemotePort", request.getRemotePort());
        rst.put("LocalName", request.getLocalName());
        rst.put("LocalAddr", request.getLocalAddr());
        rst.put("LocalPort", request.getLocalPort());
        rst.put("AuthType", request.getAuthType());
        rst.put("Cookies", request.getCookies());

        Map<String, Object> headers = new HashMap<String, Object>();
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        rst.put("headers", headers);

        rst.put("Method", request.getMethod());
        rst.put("PathInfo", request.getPathInfo());
        rst.put("PathTranslated", request.getPathTranslated());
        rst.put("ContextPath", request.getContextPath());
        rst.put("QueryString", request.getQueryString());
        rst.put("RemoteUser", request.getRemoteUser());
        rst.put("UserPrincipal", request.getUserPrincipal());
        rst.put("RequestedSessionId", request.getRequestedSessionId());
        rst.put("RequestURI", request.getRequestURI());
        rst.put("RequestURL", request.getRequestURL());
        rst.put("ServletPath", request.getServletPath());
        rst.put("isRequestedSessionIdValid", request.isRequestedSessionIdValid());
        rst.put("isRequestedSessionIdFromCookie", request.isRequestedSessionIdFromCookie());
        rst.put("isRequestedSessionIdFromURL", request.isRequestedSessionIdFromURL());

        try {
            rst.put("Reader", FileCopyUtils.copyToString(request.getReader()));
        } catch (IOException e) {
            logger.error("读取请求流失败", e);
        }

        return rst;
    }

    @RequiresRoles(SecurityUtils.SUPER_ADMIN_NAME)
    @RequestMapping("configInfo")
    @ResponseBody
    public Map<String, Object> configInfo() {
        Map<String, Object> rst = new HashMap<String, Object>();
        Set<String> keys = ConfigUtils.get().keySetStartWith(null);
        for (String key : keys) {
            rst.put(key, ConfigUtils.get().getValue(key));
        }
        return rst;
    }
}
