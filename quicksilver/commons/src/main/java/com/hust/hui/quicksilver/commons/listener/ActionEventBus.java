package com.hust.hui.quicksilver.commons.listener;

import com.google.common.eventbus.EventBus;

/**
 * Created by yihui on 2017/3/2.
 */
public class ActionEventBus {

    private final static EventBus eventBus = new EventBus();


    public static void post(Object event) {
        eventBus.post(event);
    }

    public static void register(Object handler) {
        eventBus.register(handler);
    }

    public static void unregister(Object handler) {
        eventBus.unregister(handler);
    }

}
