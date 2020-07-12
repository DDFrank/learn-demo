package com.hust.hui.quicksilver.commons.test.listener.eventbus.types;

import com.google.common.reflect.TypeToken;
import com.hust.hui.quicksilver.commons.test.listener.eventbus.audit.AuditEvent;
import org.junit.Test;

import java.util.Set;

/**
 * Created by yihui on 2017/4/13.
 */
public class SuperTypeTest {

    interface TestInterface {

    }

    static class TestEvent extends AuditEvent implements TestInterface {
    }


    @Test
    public void testGetSuperClass() {
        Set<Class<? super TestEvent>> set = TypeToken.of(TestEvent.class).getTypes().rawTypes();
        System.out.println(set);
    }

}
