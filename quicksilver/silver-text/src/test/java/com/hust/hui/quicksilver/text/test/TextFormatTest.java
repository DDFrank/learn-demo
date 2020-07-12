package com.hust.hui.quicksilver.text.test;

import com.hust.hui.quicksilver.text.TextFormat;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yihui on 2017/3/28.
 */
public class TextFormatTest {

    @Test
    public void testReplace() {
        String text = "hello {user}, welcome to {place}!";
        String user = "Lucy";
        String place = "China";

        String res = text.replace("{user}", user).replace("{place}", place);
        System.out.println(res);
    }


    public String replace(String text, Object... args) {
        return MessageFormat.format(text, args);
    }


    @Test
    public void testReplace2() {
        String text = "hello {0}, welcome to {1}!";
        String user = "Lucy";
        String place = "China";

        String ans = replace(text, user, place);
        System.out.println(ans);


        text = "hello {0}, welcome to {2} ! what's a good day! today is {1}!";
        ans = replace(text, "Lucy", new Date(), "HangZhou");
        System.out.println(ans);


        text = "hello {0}, welcome to {2} ! what's {0}' a good day! today is {1}!";
        ans = replace(text, "Lucy", new Date(), "HangZhou");
        System.out.println(ans);
    }


    // 获取patter的过程较为负责,这里初始化时,做一次即可
    private static Pattern pattern;

    static {
        pattern = Pattern.compile("((?<=\\{)([a-zA-Z_]{1,})(?=\\}))");
    }

    public String replaceV2(String text, Map<String, Object> map) {
        List<String> keys = new ArrayList<>();

        // 把文本中的所有需要替换的变量捞出来, 丢进keys
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String key = matcher.group();
            if (!keys.contains(key)) {
                keys.add(key);
            }
        }

        // 开始替换, 将变量替换成数字,  并从map中将对应的值丢入 params 数组
        Object[] params = new Object[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            text = text.replaceAll(keys.get(i), i + "");
            params[i] = map.get(keys.get(i) + "");
        }


        return replace(text, params);
    }


    public String replaceV3(String text, Map<String, Object> map) {
        // 把文本中的所有需要替换的变量捞出来, 丢进keys
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String key = matcher.group();
            text = text.replaceAll("\\{" + key + "\\}", map.get(key) + "");
        }

        return text;
    }


    @Test
    public void testReplaceV3() {
        String text = "hello {user}, welcome to {place}! {place} is a beautiful place!";

        Map<String, Object> map = new HashMap<>(2);
        map.put("user", "Lucy");
        map.put("place", "China");

        String res = replaceV3(text, map);
        System.out.println(res);
    }


    @Test
    public void testReplaceV2() {
        String text = "hello {user}, welcome to {place}! now timestamp is: {time} !";

        Map<String, Object> map = new HashMap<>(2);
        map.put("user", "Lucy");
        map.put("place", "China");
        map.put("time", System.currentTimeMillis());

        String res = replaceV2(text, map);
        System.out.println(res);
    }


    public List<String> replaceV4(String text, List<Map<String, Object>> mapList) {
        List<String> keys = new ArrayList<>();

        // 把文本中的所有需要替换的变量捞出来, 丢进keys
        Matcher matcher = pattern.matcher(text);
        int index = 0;
        while (matcher.find()) {
            String key = matcher.group();
            if (!keys.contains(key)) {
                keys.add(key);
                text = text.replaceAll(keys.get(index), index + "");
                index ++;
            }
        }


        List<String> result = new ArrayList<>();
        // 开始替换, 将变量替换成数字,  并从map中将对应的值丢入 params 数组
        for (Map<String, Object> map: mapList) {
            Object[] params = new Object[keys.size()];
            for (int i = 0; i < keys.size(); i++) {
                params[i] = map.get(keys.get(i) + "");
            }

            result.add(replace(text, params));
        }

        return result;
    }


//    --------------------------

    @Test
    public void testTextV1() {
        String text = "{user}, today is {date}! what's a good day! Welcome {user} to {place}!";
        Map<String, Object> params = new HashMap<>();
        params.put("user", "Lucy");
        params.put("date", new Date());
        params.put("place", "HangZhou");


        String ans = TextFormat.format(text, params);
        System.out.println(ans);


        params.remove("place");
        ans = TextFormat.format(text, params);
        System.out.println(ans);


        String text2 = "What's a good day!";
        ans = TextFormat.format(text2, params);
        System.out.println(ans);


        params.clear();
        ans = TextFormat.format(text, params);
        System.out.println(ans);
    }


    @Test
    public void testTextV2() {
        String text = "{user}, today is {date}! Welcome {user} to {place}!";

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("user", "Lucy");
        params.put("date", new Date());
        params.put("place", "HangZhou");
        list.add(params);


        Map<String, Object> params2 = new HashMap<>();
        params2.put("user", "Lily");
        params2.put("date", new Date());
        params2.put("place", "SuZhou");
        list.add(params2);


        List<String> ans = TextFormat.batchFormat(text, list);
        System.out.println(ans);


        // 当text中包含 ' 时, 使用 MessageFormat 进行替换有个bug, 单引号后面的不会替换
        text = "{user}, today is {date}! What's a good day! Welcome {user} to {place}!";
        ans = TextFormat.batchFormat(text, list);
        System.out.println(ans);
    }



    @Test
    public void testTextV3() {
        String text = "{user}, today is {date}! Welcome {user} to {place}!";

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("user", "Lucy");
        params.put("date", new Date());
        params.put("place", "HangZhou");
        list.add(params);


        Map<String, Object> params2 = new HashMap<>();
        params2.put("user", "Lily");
        params2.put("date", new Date());
        params2.put("place", "SuZhou");
        list.add(params2);


        List<String> ans = TextFormat.batchFormatV2(text, list);
        System.out.println(ans);


        // 当text中包含 ' 时, 使用 MessageFormat 进行替换有个bug, 单引号后面的不会替换
        text = "{user}, today is {date}! What's a good day! Welcome {user} to {place}!";
        ans = TextFormat.batchFormatV2(text, list);
        System.out.println(ans);



        String ans2 = TextFormat.formatV2(text, params);
        System.out.println(ans2);
    }

}
