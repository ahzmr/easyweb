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
        short s = -1;
        System.out.println((short)0xffff);
        System.out.println((short)0x7fff);
        System.out.println((short)0x8000);
        System.out.println((-s) << 16);
        System.out.println(s << 16);
        System.out.println((s << 16) + 32693);
    }

}
