package com.wenin819.easyweb.other.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lc3@yitong.com.cn
 */
public class Log4jTest {

    private static Logger logger = LoggerFactory.getLogger(Log4jTest.class);
    private static Logger myLogger = LoggerFactory.getLogger("myLogger");

    public static void main(String[] args) {
        logger.info("开始执行……");
        myLogger.info("开始执行……");
        myLogger.info("执行结束");
        logger.info("执行结束");
    }
}
