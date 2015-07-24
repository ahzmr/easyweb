package com.wenin819.easyweb.utils;

import org.apache.http.HttpHost;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Http 请求上下文
 * Created by wenin819@gmail.com on 2014-08-26
 */
public class HttpContext {

    private String url;
    private Map<String, String> params;
    private Map<String, String> cookies;
    private HttpHost proxy;
    private Charset charset;

    public HttpContext(String url) {
        this.url = url;
    }

    public HttpContext(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public HttpContext(String url, Map<String, String> params, Map<String, String> cookies) {
        this.url = url;
        this.params = params;
        this.cookies = cookies;
    }

    public String getUrl() {
        return url;
    }

    public HttpContext setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public HttpContext addParam(String name, String value) {
        if(null == params) {
            params = new LinkedHashMap<String, String>();
        }
        params.put(name, value);
        return this;
    }

    public HttpContext addAllParam(Map<String, String> params) {
        if(null == this.params) {
            this.params = new LinkedHashMap<String, String>();
        }
        this.params.putAll(params);
        return this;
    }

    public HttpContext addAllParam(String[] names, String[] values) {
        if(null == params) {
            params = new LinkedHashMap<String, String>();
        }
        for (int i = 0; i < names.length; i++) {
            params.put(names[i], values[i]);
        }
        return this;
    }

    public HttpContext setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public HttpContext addCookie(String name, String value) {
        if(null == cookies) {
            cookies = new LinkedHashMap<String, String>();
        }
        cookies.put(name, value);
        return this;
    }

    public HttpContext addAllCookie(Map<String, String> cookies) {
        if(null == this.cookies) {
            this.cookies = new LinkedHashMap<String, String>();
        }
        this.cookies.putAll(cookies);
        return this;
    }

    public HttpContext addAllCookie(String[] names, String[] values) {
        if(null == cookies) {
            cookies = new LinkedHashMap<String, String>();
        }
        for (int i = 0; i < names.length; i++) {
            cookies.put(names[i], values[i]);
        }
        return this;
    }

    public HttpContext setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public HttpHost getProxy() {
        return proxy;
    }

    public HttpContext setProxy(HttpHost proxy) {
        this.proxy = proxy;
        return this;
    }

    public Charset getCharset() {
        return charset;
    }

    public HttpContext setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }
}
