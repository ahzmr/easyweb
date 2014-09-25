package com.wenin819.easyweb.modules.webclient.service;

import com.wenin819.easyweb.modules.webclient.util.HttpUtils;
import com.wenin819.easyweb.modules.webclient.vo.HttpContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * Created by wenin819@gmail.com on 2014-08-26
 */
@Service
public class WebClientService {

    private Map<String, HttpContext> httpContextMap = new HashMap<>();
    private final String imageUrl = "http://web.ahnw.gov.cn/xzxchqn/Getcode.asp";
    private final String formUrl = "http://web.ahnw.gov.cn/xzxchqn/SaveVote.asp";
    public final String userId = "1759";
    public String isCity = "1";

    private static final List<String> proxies = new LinkedList<>();

    static {
        final Resource resource = new DefaultResourceLoader().getResource("modules/webclient/proxy_list.txt");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            final String s = IOUtils.toString(inputStream);
            final String[] strings = s.split("\n");
            for (String string : strings) {
                if(StringUtils.hasText(string)) {
//                    System.out.println("add proxy:" + string);
                    proxies.add(string.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public String commit(String id, String code) throws Exception {
        HttpContext httpContext = httpContextMap.get(id);
        if(null == httpContext) {
            throw new Exception("表单已失效，请忽略此表单");
        }
        if(formUrl != httpContext.getUrl()) {
            httpContext.setUrl(formUrl);
        }
        httpContext.addParam("code", code);
        for (int i = 0; i < 30; i++) {
            final String rs = HttpUtils.httpPost(httpContext);
            final String msg = getResultMsg(rs);
            if(null == msg || "每天同一IP投票只能投3次".equals(msg) || "该IP投票已超过30次".equals(msg)) {
                updateProxy(httpContext);
                continue;
            }
            if("投票成功".equals(msg)) {
//                httpContextMap.remove(id);
            }
            return msg;
        }
        throw new Exception("尝试多次，都失败，请稍后再试");
    }

    private void removeProxy(HttpHost proxy) {
        if(null == proxy) {
            return;
        }
        String host = proxy.getHostName() + ":" + proxy.getPort();
        proxies.remove(host);
    }

    private void updateProxy(final HttpContext httpContext) throws Exception {
        removeProxy(httpContext.getProxy());
        if(proxies.isEmpty()) {
            throw new Exception("代理IP已经用完，请联系管理员。");
        }
        final String[] strings = proxies.get(0).split(":");
        httpContext.setProxy(new HttpHost(strings[0], Integer.parseInt(strings[1])));
    }

    private String getResultMsg(String result) {
        if(null == result) {
            return result;
        }
        final int idx = result.indexOf("alert(");
        if(-1 != idx) {
            int start = idx + 7;
            int end = result.indexOf("'", start);
            if(-1 != end && end < result.length()) {
                return result.substring(start, end);
            }
        }
        return null;
    }

    public List<String> genNewHttpContextIdList() {
        List<String> rsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            rsList.add(genNewHttpContextId());
        }
        return rsList;
    }

    public String genNewHttpContextId() {
        final String id = UUID.randomUUID().toString().replaceAll("-", "");
        httpContextMap.put(id, genNewHttpContext());
        return id;
    }

    public HttpContext genNewHttpContext() {
        HttpContext httpContext = new HttpContext(imageUrl);
        httpContext.addParam("Id", userId);
        httpContext.addParam("isCity", isCity);
        return httpContext;
    }

    public byte[] getImage(String id) {
        HttpContext httpContext = httpContextMap.get(id);
        if(null == httpContext) {
            httpContextMap.put(id, httpContext = genNewHttpContext());
            System.out.println("warn :: not exists httpContext");
        }
        if(imageUrl != httpContext.getUrl()) {
            httpContext.setUrl(imageUrl);
        }
        return HttpUtils.httpGetBytes(httpContext);
    }

}
