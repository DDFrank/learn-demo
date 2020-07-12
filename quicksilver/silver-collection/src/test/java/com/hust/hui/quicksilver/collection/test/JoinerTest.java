package com.hust.hui.quicksilver.collection.test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.*;

/**
 * Created by yihui on 2017/8/30.
 */
public class JoinerTest {


    @Test
    public void testJoiner() {
        List<String> list = new ArrayList<String>() {
            {
                add("12");
                add("@#");
                add("absc");
            }
        };

        String ans = Joiner.on(",").join(list);
        System.out.println("join: " + ans);

        String[] strs = new String[]{"123", "456", "789", "asdf"};
        ans = Joiner.on("&").join(strs);
        System.out.println(ans);


        Map<String, Object> params = new HashMap<String, Object>() {
            {
                put("key1", "123");
                put("key2", 678);
                put("key3", "what");
            }
        };

        ans = Joiner.on("&").withKeyValueSeparator("=").join(params);
        System.out.println("map->" + ans);
    }


    @Test
    public void testSplit() {
        String ans = "key1=123&key2=678&key3=what";

        Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split(ans);
        System.out.println("split map: " + map);


        ans = "123&456&789&asdf";
        List<String> list = Splitter.on("&").splitToList(ans);
        System.out.println("split list: " + list);
    }


    @Test
    public void testSort() {
        List<Integer> list = new ArrayList<Integer>() {
            {
                add(20);
                add(5);
                add(40);
                add(null);
                add(1);
            }
        };

        Collections.sort(list, (o1, o2) -> {
            if (o1 == null) {
                return -1;
            }

            if (o2 == null) {
                return 1;
            }

            return o1 - o2;
        });

        System.out.println(list);

        Collections.sort(list);
    }


    @Test
    public void testMod() {
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("world");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            System.out.println("old: " + str);
            str = "xiami";
            System.out.println(str);
        }

        System.out.println(list);
    }


    @Test
    public void testEn() {
        List<String> list = new ArrayList<>(2);
        list.add("12");
        list.add("23");
        list.add("45");

        List<String> sub = new ArrayList<String>();
        sub.add("hello");
        sub.add("world");
        sub.add("set");
        sub.add("set");
        sub.add("set");
        list.addAll(sub);
        System.out.println(list);
    }
}
