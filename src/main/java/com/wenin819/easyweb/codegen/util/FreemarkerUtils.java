package com.wenin819.easyweb.codegen.util;

import freemarker.template.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.io.Writer;

/**
 * freemarker 工具类.
 * Created by wenin819@gmail.com on 2014/4/9.
 */
public class FreemarkerUtils {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtils.class);

    public static String process2String(Template tpl, Object params) {
        Writer strWritter = new StringWriter();
        try {
            tpl.process(params, strWritter);
        } catch (Exception e) {
            logger.error("模板生成失败", e);
        }
        return strWritter.toString();
    }
}
