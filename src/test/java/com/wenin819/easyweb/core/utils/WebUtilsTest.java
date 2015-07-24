package com.wenin819.easyweb.core.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author wenin819@gmail.com
 * @date 2015-07-24.
 */
public class WebUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(WebUtilsTest.class);

    @Test
    public void testGetLocationNameByIp() throws Exception {
        String[] ips = new String[] {"120.210.166.243", "120.210.161.214"};
        for (String ip : ips) {
            logger.info("{}: {}", ip, WebUtils.getLocationNameByIp(ip));
        }
    }
}