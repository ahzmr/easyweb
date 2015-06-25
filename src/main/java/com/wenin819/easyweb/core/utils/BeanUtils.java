package com.wenin819.easyweb.core.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 反映对象属性操作工具类
 * @author wenin819@gmail.com
 */
public abstract class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    public static final <T> T setProperty(T bean, String name, Object value, boolean isRequired) {
        Assert.notNull(bean, "bean不能为空！");
        Assert.notNull(name, "name不能为空！");
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            if(isRequired) {
                String message = bean.getClass().getName() + "不能设置属性" +
                        name + "的值为" + value;
                logger.warn(message, e);
                throw new IllegalArgumentException(message, e);
            }
        }
        return bean;
    }
}
