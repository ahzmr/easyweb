package com.wenin819.easyweb.modules.cxf;

import javax.jws.WebService;

/**
 * @author lc3@yitong.com.cn
 */
@WebService
public interface HelloWorld {
    String sayHi(String text);
}
