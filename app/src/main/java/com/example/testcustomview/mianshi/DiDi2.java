package com.example.testcustomview.mianshi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 设计一个文件下载器，管理最大N个文件，丢弃策略是最少使用次数的文件
public class DiDi2 {

    private final Object lock = new Object();

    private static volatile DiDi2 INSTANCE;

    private int maxFileCount = 5;

    HashMap<String, File> downloadedFileMap = new HashMap<>();
    HashMap<String, Integer> fileUseCountMap = new HashMap<>();

    private DiDi2() {
        ScheduledThreadPoolExecutor singleExecutor = new ScheduledThreadPoolExecutor(1);
        singleExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                clean();
            }
        }, 5000, 5000, TimeUnit.SECONDS);
    }

    public DiDi2 getInstance() {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    INSTANCE = new DiDi2();
                }
            }
        }
        return INSTANCE;
    }

    public interface Callback {
        void onCall(File file);
    }

    public void getFile(Callback callback, String path) {
        File file;
        if (downloadedFileMap.containsKey(path)) {
            file = downloadedFileMap.get(path);
        } else {
            // 加载/下载
            file = new File(path);
        }
        int count = fileUseCountMap.getOrDefault(path, 0);
        fileUseCountMap.put(path, count + 1);
        callback.onCall(file);
    }

    private void clean() {
        if (downloadedFileMap.size() > maxFileCount) {
            int minUseCount = Integer.MAX_VALUE;
            String minCountKey = "";
            Set<Map.Entry<String, Integer>> set = fileUseCountMap.entrySet();
            for (Map.Entry<String, Integer> entry : set) {
                int fileUseCount = entry.getValue();
                if (fileUseCount < minUseCount) {
                    minUseCount = fileUseCount;
                    minCountKey = entry.getKey();
                }
            }
            downloadedFileMap.remove(minCountKey);
        }
    }
}
