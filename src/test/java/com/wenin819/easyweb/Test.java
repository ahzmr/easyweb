package com.wenin819.easyweb;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wenin819@gmail.com on 2014-06-03.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://user.goodjobs.cn/dispatcher.php/module/Resume/action/Show/id/xinan%2E");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 3128));
        URLConnection urlConnection = url.openConnection(proxy);
        urlConnection.setRequestProperty("Cookie", "PHPSESSID=dtsul5dbddj14ua2bgjta1m4f3");
        System.out.println(IOUtils.toString(urlConnection.getInputStream(), "GBK"));
    }

}
