package com.wenin819.easyweb.core.utils.observe;

/**
 * 被观察者标记接口
 *  被标记的接口要实现此接口： <code>public static GenericObservable getObservable();</code>
 *
 * @author wenin819@gmail.com
 */
public interface ObservableTag {

    /**
     * 被观察者标签对应的被观察者获取接口名称
     */
    String OBSERVABLE_TAG_METHOD_NAME = "getObservable";

}
