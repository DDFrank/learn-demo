package com.frank.concurrency.example.publish;

import com.frank.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.publish
 * @Description: ${todo}
 * @date 2018/9/2下午6:58
 */
@NotThreadSafe
@Slf4j
public class UnsafePublish {

    private String[] states = {"a", "b", "c"};

    public String[] getStates() {
        return states;
    }

    /*
    * 对象发布后数据会被随意修改，所以线程不安全
    * */
    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("{}", Arrays.toString(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "d";
        log.info("{}", Arrays.toString(unsafePublish.getStates()));
    }
}
