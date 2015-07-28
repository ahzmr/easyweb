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
    private final Map<String, String> params = new LinkedHashMap<String, String>();
    private final Map<String, String> cookies = new LinkedHashMap<String, String>();
    private final Map<String, String> headers = new LinkedHashMap<String, String>();
    private HttpHost proxy;
    private Charset charset;

    public HttpContext(String url) {
        this.url = url;
    }

    public HttpContext(String url, Map<String, String> params) {
        this.url = url;
        addAllParam(params);
    }

    public HttpContext(String url, Map<String, String> params, Map<String, String> cookies) {
        this.url = url;
        addAllParam(params);
        addAllCookie(cookies);
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
        params.put(name, value);
        return this;
    }

    public HttpContext addAllParam(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public HttpContext addAllParam(String[] names, String[] values) {
        for (int i = 0; i < names.length; i++) {
            addParam(names[i], values[i]);
        }
        return this;
    }

    public HttpContext clearParams() {
        params.clear();
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public HttpContext addCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public HttpContext addAllCookie(Map<String, String> cookies) {
        this.cookies.putAll(cookies);
        return this;
    }

    public HttpContext addAllCookie(String[] names, String[] values) {
        for (int i = 0; i < names.length; i++) {
            addCookie(names[i], values[i]);
        }
        return this;
    }

    public HttpContext clearCookies() {
        this.cookies.clear();
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpContext addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public HttpContext addAllHeader(Map<String, String> headers) {
        this.headers.putAll(cookies);
        return this;
    }

    public HttpContext addAllHeader(String[] names, String[] values) {
        for (int i = 0; i < names.length; i++) {
            addHeader(names[i], values[i]);
        }
        return this;
    }

    public HttpContext clearHeaders() {
        this.headers.clear();
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
