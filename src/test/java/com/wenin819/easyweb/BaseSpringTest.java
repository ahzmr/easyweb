package com.wenin819.easyweb;

import com.wenin819.easyweb.core.util.JsonUtils;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring测试基类
 * Created by wenin819@gmail.com on 2014/4/7.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-context.xml"})
public class BaseSpringTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected void loggerJson(Object... msgs) {
        if(logger.isInfoEnabled()) {
            logger.info(JsonUtils.toJsonString(msgs));
        }
    }
}
