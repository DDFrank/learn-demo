package com.frank.concurrency.example.publish;

import com.frank.concurrency.annotations.NotRecommand;
import com.frank.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 016039
 * @Package com.frank.concurrency.example.publish
 * @Description: ${todo}
 * @date 2018/9/2下午7:01
 */
@Slf4j
@NotThreadSafe
@NotRecommand
public class Escape {
    private int thisCanBeEscape = 0;

    public Escape() {
        new InnerClass();
    }

    private class InnerClass{
        public InnerClass() {
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
