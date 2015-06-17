package com.wenin819.easyweb.thread;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lc3@yitong.com.cn
 */
public class TestScenario {
    private static final Map<String, ThreadEntry> threadEntryMap = new HashMap<String, ThreadEntry>();

    public static void main(String[] args) {
        Map<String, List<String>> prevSetting = new HashMap<String, List<String>>();
        prevSetting.put("2", Arrays.asList("1"));
        prevSetting.put("3", Arrays.asList("1"));
        prevSetting.put("4", Arrays.asList("2", "3"));
        prevSetting.put("5", Arrays.asList("3"));
        prevSetting.put("6", Arrays.asList("4", "5"));
        prevSetting.put("7", Arrays.asList("6"));

        for (int i = 7; i > 0; i--) {
            String name = String.valueOf(i);
            ThreadEntry entry = new ThreadEntry(new ThreadTest(name), threadEntryMap, prevSetting.get(name));
            threadEntryMap.put(name, entry);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 7; i > 0; i--) {
            executorService.submit(threadEntryMap.get(String.valueOf(i)));
        }
        executorService.shutdown();
    }
}
