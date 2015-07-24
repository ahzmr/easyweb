package eu.bitwalker.useragentutils;

import com.wenin819.easyweb.core.utils.JsonUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenin819@gmail.com
 * @date 2015-07-24.
 */
public class UserAgentTest {

    private static final Logger logger = LoggerFactory.getLogger(UserAgentTest.class);

    @Test
    public void testParseUserAgentString() {
        UserAgent userAgent = UserAgent.parseUserAgentString(
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
        logger.info("UserAgent: ", JsonUtils.objectToJson(userAgent));
    }
}
