package com.wenin819.easyweb.thread;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author lc3@yitong.com.cn
 */
public class ThreadEntry extends Thread {
    private ThreadTest target;
    private List<String> prerequisite;
    private Map<String, ThreadEntry> threadEntryMap;

    public ThreadEntry(ThreadTest target, Map<String, ThreadEntry> threadEntryMap, List<String>  prerequisite) {
        Assert.notNull(target, "target Runnable不能为空");
        this.target = target;
        this.threadEntryMap = threadEntryMap;
        this.prerequisite = prerequisite;
    }

    private ThreadEntry prevThread() {
        if(null == prerequisite) {
            return null;
        }
        for (String s : prerequisite) {
            ThreadEntry threadEntry = threadEntryMap.get(s);
            if(null == threadEntry) {
                continue;
            }
            if(State.TERMINATED != threadEntry.getState()) {
                return threadEntry;
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
            System.err.println("--- " + target.getName() + " ---");
            while(true) {
                ThreadEntry prevThread = prevThread();
                if (null != prevThread) {
                    prevThread.wait();
                } else {
                    target.run();
                    notifyAll();
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
