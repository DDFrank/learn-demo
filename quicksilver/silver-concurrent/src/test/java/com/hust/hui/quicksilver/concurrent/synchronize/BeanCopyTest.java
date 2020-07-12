package com.hust.hui.quicksilver.concurrent.synchronize;

import com.hust.hui.quicksilver.concurrent.synchronize.copier.DestB;
import com.hust.hui.quicksilver.concurrent.synchronize.copier.SourceA;
import net.sf.cglib.beans.BeanCopier;
import org.junit.Test;

/**
 * Created by yihui on 2017/12/17.
 */
public class BeanCopyTest {

    @Test
    public void testCopy() {
        BeanCopier beanCopier = BeanCopier.create(SourceA.class, DestB.class, false);

        SourceA a = new SourceA("hello", 20);
        DestB b = new DestB();

        beanCopier.copy(a, b, null);

        System.out.println("a: " + a + "\nb:" + b);
    }
}
