package com.wenin819.easyweb.core.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * @author wenin819@gmail.com
 * @date 2015-07-21.
 */
public class ConfigUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(ConfigUtilsTest.class);

    @Test
    public void testGet() throws Exception {
        CharSequence charSequence = ConfigUtils.get().getValue(Configs.JDBC_TYPE, CharSequence.class, null);
        logger.info("charSequence: {}", charSequence);
        assertNotNull(charSequence);
    }
}