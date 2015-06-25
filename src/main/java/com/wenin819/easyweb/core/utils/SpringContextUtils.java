package com.wenin819.easyweb.core.utils;

import com.wenin819.easyweb.core.utils.observe.GenericObservable;
import com.wenin819.easyweb.core.utils.observe.ObservableTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * SpringContext工具类
 *
 * @author wenin819@gmail.com
 */
@Service
@Lazy(false)
public class SpringContextUtils implements ApplicationContextAware, DisposableBean, ObservableTag {

    private static ApplicationContext applicationContext;
    private static final byte[] lock = new byte[0];

    private static Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);

    private static final GenericObservable observable = new GenericObservable(SpringContextUtils.class.getName());
    public static GenericObservable getObservable() {
        return observable;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (lock) {
            SpringContextUtils.applicationContext = applicationContext;
            observable.setChanged();
            observable.notifyObservers();
        }
    }

    public static ApplicationContext getApplicationContext() {
        if(null != applicationContext) {
            return applicationContext;
        }
        synchronized (lock) {
            if(null != applicationContext) {
                return applicationContext;
            }
        }
        return applicationContext;
    }

    /**
     * 通过Bean的名称得到bean
     * @param name bean名称
     * @param requiredType bean类型
     * @param <T> bean类型
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    /**
     *
     * @param requiredType bean类型
     * @param <T> bean类型
     * @return bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }



    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    @Override
    public void destroy() {
        if (logger.isDebugEnabled()){
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }
        synchronized (lock) {
            applicationContext = null;
        }
        ExecutorUtils.getSystemScheduledExecutorService().shutdown();
    }
}
