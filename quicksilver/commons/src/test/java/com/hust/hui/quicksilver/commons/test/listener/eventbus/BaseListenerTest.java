package com.hust.hui.quicksilver.commons.test.listener.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui on 2017/3/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-service.xml"})
public class BaseListenerTest {


    private static EventBus eventBus = new EventBus();

    @Before
    public void register() {
        eventBus.register(this);
    }


    @Subscribe
    public void onEvent1(String obj) {
        System.out.println("String: " + obj);
    }



    @Subscribe
    public void onEvent2(Long hah) {
        System.out.println("long: " + hah);
    }


    @Subscribe
    public void onEvent3(Integer inVal) {
        System.out.println("int: " + inVal);
    }

    @Test
    public void testPushMsg() {
        String str = "hahha lalal";

        eventBus.post(str);
        System.out.println("----");


        // post 2
        eventBus.post(1000L);
        System.out.println("-------");


        // post 3
        eventBus.post(100);
        System.out.println("----------");
    }

}
