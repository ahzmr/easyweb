package com.wenin819.easyweb.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Web 访问工具类
 * Created by wenin819@gmail.com on 2014-08-25
 */
public class HttpUtils {

    public static final Charset DEFAULT_HTTP_ENCODING = Charset.forName("UTF-8");
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(2000).setConnectTimeout(2000).build();

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 得到查询串，用特定字符分隔
     * @param params    参数
     * @param separator 连接符
     * @return
     */
    public static String genQueryString(Map<String, String> params, String separator) {
        if(null == params || params.isEmpty()) {
            return "";
        }
        if(null == separator) {
            separator = "&";
        }
        StringBuilder s = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if(isFirst) {
               isFirst = false;
            } else {
                s.append(separator);
            }
            s.append(entry.getKey()).append('=').append(Objects.toString(entry.getValue(), ""));
        }
        return s.toString();
    }

    private static Map<String, String> getCookies(CloseableHttpResponse response) {
        Map<String, String> cookies = new LinkedHashMap<String, String>();
        if(null == response) {
            return cookies;
        }
        final Header[] headers = response.getAllHeaders();
        String cookieStr = null;
        for (Header header : headers) {
            final String name = header.getName();
            if("Set-Cookie".equalsIgnoreCase(name)) {
                cookieStr = header.getValue();
                break;
            }
        }
        if(null != cookieStr) {
            final String[] cookieStrs = cookieStr.split(";");
            for (String str : cookieStrs) {
                int idx = str.indexOf('=');
                if(-1 == idx) {
                    continue;
                }
                cookies.put(str.substring(0, idx), str.substring(idx + 1));
            }
        }
        return cookies;
    }

    /**
     * Http Get请求
     * @return
     */
    public static String httpGet(HttpContext httpContext) {
        final byte[] bytes = httpGetBytes(httpContext);
        String rs = null;
        try {
            rs = null == bytes ? null : new String(bytes, null != httpContext.getCharset() ?
                    httpContext.getCharset() : DEFAULT_HTTP_ENCODING);
        } catch (Exception e) {
            logger.error("转换httpGet请求返回报文失败", e);
        }
        if(logger.isTraceEnabled()) {
            logger.trace("response context: [{}]", rs);
        }
        return rs;
    }

    public static byte[] httpGetBytes(HttpContext httpContext) {
        final HttpClientBuilder builder = HttpClients.custom();
        if(null != httpContext.getProxy()) {
            builder.setProxy(httpContext.getProxy());
        }
        final CloseableHttpClient httpClient = builder.build();

        String url = httpContext.getUrl();
        Map<String, String> params = httpContext.getParams();
        if(null != params && !params.isEmpty()) {
            if (-1 == url.indexOf('?')) {
                url += "?";
            } else {
                url += "&";
            }
            url += genQueryString(params, "&");
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        Map<String, String> cookies = httpContext.getCookies();
        httpGet.addHeader("Cookie", genQueryString(cookies, "; "));
        try {
            final CloseableHttpResponse response = httpClient.execute(httpGet);
            httpContext.addAllCookie(getCookies(response));
            final HttpEntity entity = response.getEntity();
            final byte[] rs = EntityUtils.toByteArray(entity);
            EntityUtils.consume(entity);
            return rs;
        } catch (IOException e) {
            logger.error("httpGet请求异常", e);
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }

    /**
     * Http Post 请求
     * @return
     */
    public static String httpPost(HttpContext httpContext) {
        final HttpClientBuilder builder = HttpClients.custom();
        if(null != httpContext.getProxy()) {
            builder.setProxy(httpContext.getProxy());
        }
        final CloseableHttpClient httpClient = builder.build();

        String url = httpContext.getUrl();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        Map<String, String> cookies = httpContext.getCookies();
        httpPost.addHeader("Cookie", genQueryString(cookies, "; "));
        try {
            Map<String, String> params = httpContext.getParams();
            Charset httpEncoding = null != httpContext.getCharset() ? httpContext.getCharset() : DEFAULT_HTTP_ENCODING;
            if(null != params && !params.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, httpEncoding));
            }
            final CloseableHttpResponse response = httpClient.execute(httpPost);
            httpContext.addAllCookie(getCookies(response));
            final HttpEntity entity = response.getEntity();
            final String rs = EntityUtils.toString(entity, httpEncoding);
            EntityUtils.consume(entity);
            return rs;
        } catch (IOException e) {
            logger.error("httpPost请求异常", e);
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }

}
