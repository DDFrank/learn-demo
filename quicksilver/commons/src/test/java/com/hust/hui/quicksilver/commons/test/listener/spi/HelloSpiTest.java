package com.hust.hui.quicksilver.commons.test.listener.spi;

import com.hust.hui.quicksilver.commons.spi.HelloInterface;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * Created by yihui on 2017/3/17.
 */
public class HelloSpiTest {

    @Test
    public void testSPI() {
        ServiceLoader<HelloInterface> serviceLoader = ServiceLoader.load(HelloInterface.class);

        for (HelloInterface hello: serviceLoader) {
            hello.sayHello();
        }
    }
}
