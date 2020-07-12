package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.test.dos.DoA;
import com.hust.hui.quicksilver.file.test.dos.DoB;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by yihui on 2017/5/10.
 */
@Slf4j
public class BeanUtilTest {
    @Test
    public void testBeanCopy() throws InvocationTargetException, IllegalAccessException {
        DoA doA = new DoA();
        doA.setName("yihui");
        doA.setPhone(1234234L);

        DoB doB = new DoB();
        BeanUtils.copyProperties(doB, doA);
        log.info("doB: {}", doB);

        BeanUtils.setProperty(doB, "name", doA.getName());
        BeanUtils.setProperty(doB, "phone", doB.getPhone());
        log.info("doB: {}", doB);
    }
}
