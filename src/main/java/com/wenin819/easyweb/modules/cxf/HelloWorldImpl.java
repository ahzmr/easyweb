package com.wenin819.easyweb.modules.cxf;

import javax.jws.WebService;

/**
 * @author lc3@yitong.com.cn
 */
@WebService(endpointInterface = "com.wenin819.easyweb.modules.cxf.HelloWorld")
public class
        HelloWorldImpl implements HelloWorld {
    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
}
