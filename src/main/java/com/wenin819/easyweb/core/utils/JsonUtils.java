package com.wenin819.easyweb.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenin819.easyweb.core.exception.DataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json 操作工具类
 * Created by wenin819@gmail.com on 2014-10-09.
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(Object obj) {
        if(null == obj) {
            return "";
        }
        if(obj instanceof CharSequence) {
            return obj.toString();
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            if(logger.isErrorEnabled()) {
                logger.error("转换Object为String失败", e);
            }
            throw new DataException("转换Object为String失败", e);
        }
    }
}
