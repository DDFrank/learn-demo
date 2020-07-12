package com.frank.concurrency.example.immutable;

import com.frank.concurrency.annotations.NotThreadSafe;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.immutable
 * @Description: ${todo}
 * @date 2018/9/3下午8:37
 */
@Slf4j
@NotThreadSafe
public class ImmutableExample1 {

    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        Collections.unmodifiableMap(map);
    }


    public static void main(String[] args) {
        map.put(1,3);
        log.info("{}", map.get(1));
    }

}
