package com.wenin819.easyweb.core.utils.observe;

import java.util.Observer;

/**
 * 可中断的观察者，如果此观察者抛出异常，则剩下的观察者们将不被通知
 * @see GenericObservable 需要配合一起使用才有效果
 * @author wenin819@gmail.com
 */
public interface InterruptObserver extends Observer {
}
