package com.wenin819.easyweb.thread;

/**
 * @author lc3@yitong.com.cn
 */
public class ThreadTest implements Runnable {

    private String name;

    public ThreadTest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        System.err.println(name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
