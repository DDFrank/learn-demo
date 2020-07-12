package com.hust.hui.quicksilver.text.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yihui on 2017/4/5.
 */
public class EncodeTest {

    @Test
    public void testENcode() {
        String str = "42 29 5e 47 40 31 d7 48 51 4f 70 57 b9 86 f6 13 bf f1 44 cd 32 15 cf d0 17 66 4f 92 67 b6 16 81 27 89 22 e9 0d ac 3e 27 65 60";
        String[] chars = StringUtils.split(str, " ");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i = i + 2) {
            stringBuilder.append("\\u").append(chars[i]).append(chars[i + 1]);
        }


        String ans = unicodeToString(stringBuilder.toString());
        System.out.println(ans);


        String ch = "中国";
        byte[] bytes = ch.getBytes();
        System.out.println(bytes);
    }


    @Test
    public void testByte2Chinese() {
        String text = "42 29 5e 47 40 31 d7 48 51 4f 70 57 b9 86 f6 13 bf f1 44 cd 32 15 cf d0 17 66 4f 92 67 b6 16 81 27 89 22 e9 0d ac 3e 27 65 60";
        String[] chars = StringUtils.split(text, " ");

        byte[] bytes = new byte[chars.length];
        int i = 0;
        for (String str : chars) {
            bytes[i++] = (byte) Integer.parseInt(str, 16);
        }

        String str = new String(bytes, Charset.defaultCharset());
        System.out.println(str);
    }

    private String int2hex(int i) {
        return Integer.toHexString(i);
    }

    private byte int2byte(int i) {
        return (byte) i;
    }

    private byte str2byte(String str) {
        return int2byte(Integer.parseInt(str, 16));
    }

    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch + "");

        }

        return str;

    }


    @Test
    public void testSort() {
        String str = "4531789,54790143,12345431,451235323";
        String[] nums = StringUtils.split(str, ",");
        List<String> list = Arrays.asList(nums);

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                long n1 = Long.parseLong(o1);
                long n2 = Long.parseLong(o2);
                if (n1 >= n2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        System.out.println(list);
    }


    private String removeId(String text, String id) {
        String[] strs = StringUtils.split(text, ",");
        List<String> list = Arrays.asList(strs);

        if (!list.contains(id)) {
            return text;
        }

        StringBuilder stringBuilder = new StringBuilder(text.length() - id.length());
        for (String str: list) {
            if (str.equals(id)) {
                continue;
            }

            stringBuilder.append(str).append(",");
        }

        if (stringBuilder.length() > 0) {
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        } else {
            return "";
        }
    }


    private String putId(String text, String id) {
        String[] strs = StringUtils.split(text, ",");

        List<String> list = Arrays.asList(strs);
        if (list.contains(id)) {
            return text;
        }

        List<String> ids = new ArrayList<>(list);
        ids.add(id);
        Collections.sort(ids, new Comparator<String>() { // 倒排, 大的放在前面
            @Override
            public int compare(String o1, String o2) {
                long n1 = Long.parseLong(o1);
                long n2 = Long.parseLong(o2);
                if (n1 >= n2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        for (String str : ids) {
            stringBuilder.append(str).append(",");
        }

        if (stringBuilder.length() > 0) {
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        } else {
            return "";
        }
    }

    @Test
    public void testSet() {
        String str = "4531789,54790143,12345431,451235323";
        String id = "12345413";

        String ans = this.putId(str, id);
        System.out.println(ans);

        ans = this.putId(str, id);
        System.out.println(ans);
    }

}
