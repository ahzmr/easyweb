package com.wenin819.easyweb.core.utils.observe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 被观察者管理器
 * @author wenin819@gmail.com
 */
public class ObservableManager {

    /**
     * 被观察者缓存
     */
    private final ConcurrentMap<Class<?>, GenericObservable> obsMap =
            new ConcurrentHashMap<Class<?>, GenericObservable>();
    private static Logger logger = LoggerFactory.getLogger(ObservableManager.class);

    /**
     * 系统默认被观察才管理器
     */
    private static final ObservableManager DEFAULT = new ObservableManager();

    /**
     * 得到默认被观察者管理器
     * @return 默认被观察管理器
     */
    public static ObservableManager get() {
        return DEFAULT;
    }

    /**
     * 得到类对应的被观察者
     * @param clazz 类
     * @return
     */
    public <E extends ObservableTag> GenericObservable getObservable(Class<E> clazz) {
        Assert.notNull(clazz, "被观察者宿主类不能为空。");
        GenericObservable observable = obsMap.get(clazz);
        if(null != observable) {
            return observable;
        }
        synchronized (obsMap) {
            observable = obsMap.get(clazz);
            if(null != observable) {
                return observable;
            }
            try {
                Method method = clazz.getMethod(ObservableTag.OBSERVABLE_TAG_METHOD_NAME);
                observable = (GenericObservable) method.invoke(null);
                obsMap.put(clazz, observable);
            } catch (Exception e) {
                String message = "请检查类结构" + clazz.getName() +
                        "，ObservableTag类中应该包含公共静态方法： public static GenericObservable " +
                        ObservableTag.OBSERVABLE_TAG_METHOD_NAME + "()。";
                logger.error(message, e);
                throw new IllegalArgumentException(message, e);
            }
        }
        return observable;
    }

}
