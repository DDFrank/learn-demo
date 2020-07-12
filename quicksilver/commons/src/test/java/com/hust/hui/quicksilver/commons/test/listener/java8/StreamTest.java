package com.hust.hui.quicksilver.commons.test.listener.java8;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by yihui on 2017/3/12.
 */
public class StreamTest {

    @Getter
    @Setter
    @ToString
    class TestStatus {
        Integer code;
        String msg;

        public TestStatus() {
        }

        public TestStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public boolean isSuccess() {
            return code == 1001;
        }
    }

    /**
     * 将数组的所有元素转换为大写
     */
    @Test
    public void testParseUp() {
        List<String> list = Arrays.asList("haha", "hello", "world");

        List<String> out = list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(out);
    }


    /**
     * 转换为 map
     */
    @Test
    public void testParseMap() {
        List<TestStatus> list = new ArrayList<>();
        list.add(new TestStatus(1001, "success"));
        list.add(new TestStatus(2002, "not exists"));
        list.add(new TestStatus(3003, "error"));
        list.add(new TestStatus(3003, "error_2"));


        try {
            Map<Integer, TestStatus> map = list.stream()
                    .filter(testStatus -> !testStatus.isSuccess())
                    .collect(Collectors.toMap(TestStatus::getCode, Function.identity()));
            System.out.println(map);
        } catch (Exception e) {
            System.out.println("some error: " + e);
        }

        Map<Integer, TestStatus> map = list.stream()
                .filter(testStatus -> !testStatus.isSuccess())
                .collect(
                        Collectors.toMap(TestStatus::getCode,
                                Function.identity(), (key1, key2) -> key2)); // 冲突时, 选择第二个
        System.out.println(map);


        // 将重复的放在list中
        Map<Integer, List<TestStatus>> obj = list.stream().collect(Collectors.groupingBy(TestStatus::getCode));
        System.out.println(obj);
    }
}
