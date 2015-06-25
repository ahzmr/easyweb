package com.wenin819.easyweb.core.utils.observe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * 系统通用被观察者实现
 * @author wenin819@gmail.com
 */
public class GenericObservable extends Observable {

    /**
     * 是否改变状态
     */
    private boolean changed = false;
    private final String name;
    /**
     * 观察者集合
     */
    private final Vector<Observer> obs = new Vector<Observer>();

    private static Logger logger = LoggerFactory.getLogger(GenericObservable.class);

    public GenericObservable() {
        name = getClass().getName();
    }

    public GenericObservable(String name) {
        Assert.notNull(name, "name不能为空。");
        this.name = name;
    }

    /**
     * 获得被观察者名称
     * @return 被观察者名称
     */
    public String getName() {
        return name;
    }

    /**
     * 注册观察者
     * @param o 观察者
     */
    public synchronized void addObserver(Observer o) {
        if (o == null) {
            throw new NullPointerException("观察都不能为空。");
        }
        if (!obs.contains(o)) {
            obs.addElement(o);
            logger.info("{}成功注册观察者：{}。", getName(), o.getClass());
        } else {
            logger.warn("{}重复注册观察者：{}，直接忽略。", getName(), o.getClass());
        }
    }

    /**
     * 删除观察者
     * @param o 观察者
     */
    public synchronized void deleteObserver(Observer o) {
        if (o == null) {
            throw new NullPointerException("观察都不能为空。");
        }
        if(obs.removeElement(o)) {
            logger.info("{}成功注销观察者：{}。", getName(), o.getClass());
        } else {
            logger.warn("{}重复注销观察者：{}，直接忽略。", getName(), o.getClass());
        }
    }

    /**
     * 向观察者们通知改变
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * 向观察者们通知改变
     * @param arg 参数
     */
    public void notifyObservers(Object arg) {
        Observer[] arrLocal;
        synchronized (this) {
            if (!changed) {
                logger.debug("{}被观察者状态未变更或已处理，忽略对观察者的通知。", getName());
                return;
            }
            arrLocal = obs.toArray(new Observer[obs.size()]);
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--) {
            Observer o = arrLocal[i];
            try {
                o.update(this, arg);
            } catch (Exception e) {
                if(o instanceof InterruptObserver) {
                    break;
                } else {
                    if(logger.isWarnEnabled()) {
                        logger.warn("观察者" + o.getClass() + "执行异常", e);
                    }
                }
            }
        }
    }

    /**
     * 删除观察者
     */
    public synchronized void deleteObservers() {
        obs.removeAllElements();
        logger.info("{}成功注销所有观察者。", getName());
    }

    /**
     * 设置状态改变，在数据发生变更时用
     */
    public synchronized void setChanged() {
        changed = true;
    }

    /**
     * 清除改变状态，在通知完成时用
     */
    protected synchronized void clearChanged() {
        changed = false;
    }

    /**
     * 判断是否改变
     * @return 是否改变
     */
    public synchronized boolean hasChanged() {
        return changed;
    }

    /**
     * 获得观察者数量
     * @return 观察者数量
     */
    public synchronized int countObservers() {
        return obs.size();
    }
}
