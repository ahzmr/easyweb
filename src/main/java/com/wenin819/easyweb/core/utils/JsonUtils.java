package com.wenin819.easyweb.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json 操作工具类
 * Created by wenin819@gmail.com on 2014-10-09.
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
//		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true); //格式化
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);	// 支持单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);	// 识别控制字符
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);	// 忽略未知属性
    }

    /**
     * object对象转换成json
     */
    public static String objectToJson(Object obj) {
        if(null == obj) {
            return "";
        }
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            if(logger.isWarnEnabled()) {
                logger.warn("转换Json失败，转换对象类型为：" + obj.getClass().getName());
            }
        }
        return jsonString;
    }

    /**
     * Json转Map
     * @param json
     * @return
     */
    public static Map<Object, Object> jsonToMap(String json) {
        if(!StringUtils.isNotEmpty(json)) {
            return new HashMap<Object, Object>(0);
        }
        try {
            //noinspection unchecked
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Json转Map失败", e);
        }
    }

    /**
     * Json转Object
     * @param json json串
     * @param clazz 返回类型
     * @param <E> VO类型
     * @return
     */
    public static <E> E jsonToObject(String json, Class<E> clazz) {
        if(!StringUtils.isNotEmpty(json)) {
            return null;
        }
        try {
            //noinspection unchecked
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Json转Map失败", e);
        }
    }

    /**
     * Json转List
     * @param json
     * @param <E>
     * @return
     */
    public static <E> List<E> jsonToList(String json) {
        if(!StringUtils.isNotEmpty(json)) {
            return new ArrayList<E>(0);
        }
        try {
            //noinspection unchecked
            return objectMapper.readValue(json, List.class);
        } catch (IOException e) {
            throw new RuntimeException("Json转List失败", e);
        }
    }

    /**
     * Json转List
     * @param json
     * @param <E>
     * @return
     */
    public static <E> List<E> jsonToList(String json, Class<E> clazz) {
        if(!StringUtils.isNotEmpty(json)) {
            return new ArrayList<E>(0);
        }
        try {
            CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            //noinspection unchecked
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException("Json转List失败", e);
        }
    }
}
