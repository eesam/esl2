package com.huasky;

import java.util.LinkedList;
import java.util.List;

public class SendThread extends Thread {
    private boolean bRunning = true;
    private static List pool = new LinkedList();
    private EmitLogTopic emitLogTopic;

    public void init() {
        emitLogTopic = new EmitLogTopic();
        emitLogTopic.init();
    }

    public void finish() {
        bRunning = false;
        synchronized (pool) {
            pool.add(pool.size(), "exit");
            pool.notifyAll();
        }

        emitLogTopic.finish();
    }

    @Override
    public void run() {
        init();
        while (bRunning) {
            try {
                String data;
                synchronized (pool) {
                    while (pool.isEmpty()) {
                        pool.wait();
                    }
                    data = (String) pool.remove(0);
                }
                // 处理收到的包
                process(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("SendThread Exit");
    }

    public void onReceiveMsg(String data) {
        synchronized (pool) {
            System.out.println("onReceiveMsg:" + data);
            pool.add(pool.size(), data);
            pool.notifyAll();
        }
    }

    private void process(String data) {
        emitLogTopic.send(data);
    }
}
