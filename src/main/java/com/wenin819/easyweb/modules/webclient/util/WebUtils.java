package com.wenin819.easyweb.modules.webclient.util;

import com.wenin819.easyweb.modules.webclient.vo.HttpContext;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Web 访问工具类
 * Created by wenin819@gmail.com on 2014-08-25
 */
public class WebUtils {

    public static final String DEFAULT_HTTP_ENCODING = "GBK";
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(2000).setConnectTimeout(2000).build();

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
            separator = "";
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
        Map<String, String> cookies = new LinkedHashMap<>();
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
            rs = new String(bytes, DEFAULT_HTTP_ENCODING);
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
//        System.out.println("response context: " + rs);
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
//            System.out.println("statusLine: " + response.getStatusLine());
            final HttpEntity entity = response.getEntity();
            final byte[] rs = EntityUtils.toByteArray(entity);
//            System.out.println("response headers: " + Arrays.toString(response.getAllHeaders()));
//            System.out.println("response rs: " + Arrays.toString(rs));
            EntityUtils.consume(entity);
            return rs;
        } catch (IOException e) {
//            e.printStackTrace();
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
            if(null != params && !params.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_HTTP_ENCODING));
            }
            final CloseableHttpResponse response = httpClient.execute(httpPost);
            httpContext.addAllCookie(getCookies(response));
//            System.out.println(response.getStatusLine());
            final HttpEntity entity = response.getEntity();
            final String rs = EntityUtils.toString(entity, DEFAULT_HTTP_ENCODING);
//            System.out.println("response headers: " + Arrays.toString(response.getAllHeaders()));
//            System.out.println(rs);
            EntityUtils.consume(entity);
            return rs;
        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
//        final HttpContext httpContext = new HttpContext("http://web.ahnw.gov.cn/xzxchqn/Getcode.asp");
//        final HttpContext httpContext = new HttpContext("http://web.ahnw.gov.cn/xzxchqn/UserInfo.asp");
//        httpGet(httpContext.addParam("id", "1720"));
//        httpGetBytes(httpContext.addParam("id", "1720"));

        final HttpContext httpContext = new HttpContext("http://web.ahnw.gov.cn/xzxchqn/SaveVote.asp");
        httpContext.addParam("Id", "1720");
        httpContext.addParam("code", "西合哈");
        httpContext.addCookie("ASPSESSIONIDCSDDASBD", "EOCIPKMBLLGJIKMLHMMBCPDA");
        httpPost(httpContext);
        System.out.printf("return cookies" + httpContext.getCookies());
    }

}
