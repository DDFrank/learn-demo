package com.hust.hui.quicksilver.queue.delayqueue;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

/**
 * Created by yihui on 2017/10/22.
 */
public class DetailManager {

    private Map<Integer, DetailInfo> realMap = new ConcurrentHashMap<>();

    private Map<String, String> cache = new ConcurrentHashMap<>();

    private Gson gson = new Gson();

    private String getCacheKey(int itemId) {
        return "detailInfo_" + itemId;
    }


    private AsyncEventBus eventBus;


    private void init() {
        DetailInfo detailInfo = new DetailInfo(1, "onw", "第一个测试", 100);
        DetailInfo detailInfo2 = new DetailInfo(2, "two", "第二个测试", 200);

        realMap.put(detailInfo.getItemId(), detailInfo);
        realMap.put(detailInfo2.getItemId(), detailInfo2);


        cache.put(getCacheKey(detailInfo.getItemId()), gson.toJson(detailInfo));
        cache.put(getCacheKey(detailInfo2.getItemId()), gson.toJson(detailInfo2));

        eventBus = new AsyncEventBus("Validate-Thread", Executors.newFixedThreadPool(2));
        eventBus.register(this);
    }


    public void updateDetail(int itemId) {
        DetailInfo detailInfo = realMap.get(itemId);
        long now = System.currentTimeMillis();
        detailInfo.setTitle("title_" + itemId + "_" + now);
        cache.put(getCacheKey(itemId), gson.toJson(detailInfo));

        // 发送一个修改的事件
        eventBus.post(new UpdateTask(itemId, now + 5000));
        System.out.println("[UpdateInfo]>>>ItemId: " + itemId + " updateTime: " + now + " validateTime: " + (now + 5000));
    }


    private DelayQueue<UpdateTask> delayQueue = new DelayQueue<>();


    /**
     * 监听修改事件
     * @param updateTask
     */
    @Subscribe
    public void verify(UpdateTask updateTask) {
        long getTaskTime = System.currentTimeMillis();
        delayQueue.put(updateTask);

        try {
            UpdateTask task = delayQueue.take();
            long processTime = System.currentTimeMillis();


            DetailInfo real = realMap.get(task.getItemId());
            String cacheObj = cache.get(getCacheKey(task.getItemId()));
            boolean ans = gson.toJson(real).equals(cacheObj);
            System.out.println("" + Thread.currentThread() + ">>>" +
                    " validate itemId: " + updateTask.getItemId() +
                    " getEventTime: " + getTaskTime +
                    " processTime:" + processTime + " ans: " + ans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        DetailManager detailManager = new DetailManager();
        detailManager.init();

        // 开始修改
        detailManager.updateDetail(1);
        Thread.sleep(20);
        detailManager.updateDetail(2);

        Thread.sleep(35000);
    }
}
