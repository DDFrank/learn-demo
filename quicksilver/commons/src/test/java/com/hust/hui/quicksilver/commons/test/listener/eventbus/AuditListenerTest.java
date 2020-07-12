package com.hust.hui.quicksilver.commons.test.listener.eventbus;

import com.google.common.eventbus.Subscribe;
import com.hust.hui.quicksilver.commons.listener.ActionEventBus;
import com.hust.hui.quicksilver.commons.test.listener.eventbus.audit.AuditEvent;
import com.hust.hui.quicksilver.commons.test.listener.eventbus.audit.AuditEventListener;
import com.hust.hui.quicksilver.commons.test.listener.eventbus.buy.BuyEvent;
import com.hust.hui.quicksilver.commons.test.listener.eventbus.buy.BuyEventEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui on 2017/3/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-service.xml"})
public class AuditListenerTest {

    private static final Logger logger = LoggerFactory.getLogger(AuditListenerTest.class);


    @Test
    public void testAudit() {
        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setAuditResultEnum(AuditEvent.AuditEventEnum.ACCEPT);
        auditEvent.setAuditUser("1hui");
        auditEvent.setRecord("test1");

        // 发布一条成功的消息
        ActionEventBus.post(auditEvent);



        auditEvent.setAuditResultEnum(AuditEvent.AuditEventEnum.REJECT);
        auditEvent.setAuditUser("2hui");
        auditEvent.setRecord("test2");
        // 发布一条拒绝的消息
        ActionEventBus.post(auditEvent);




        BuyEvent buyEvent = new BuyEvent();
        buyEvent.setBuyerUser("3hui");
        buyEvent.setCount(1);
        buyEvent.setTotalPrice(10000L);
        buyEvent.setItem("java book");
        buyEvent.setBuyEventEnum(BuyEventEnum.PAY);
        ActionEventBus.post(buyEvent);
        System.out.println("over");
    }


    class AuditEventListenerV2 extends AuditEventListener {

//        @Subscribe
//        @Override
//        public void invoke(AuditEvent args) {
//            System.out.println("AuditEventListenerV2 auditEvent..." + args);
//        }

        @Subscribe
        private void invoke(BuyEvent args) {
            System.out.println("AuditEventListenerV2 buyEvent..." + args);
        }
    }

    @Test
    public void testThread() {

        ActionEventBus.register(new AuditEventListenerV2());

        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setAuditResultEnum(AuditEvent.AuditEventEnum.ACCEPT);
        auditEvent.setAuditUser("1hui");
        auditEvent.setRecord("test1");

        // 发布一条成功的消息
        ActionEventBus.post(auditEvent);


        BuyEvent buyEvent = new BuyEvent();
        buyEvent.setBuyerUser("3hui");
        buyEvent.setCount(1);
        buyEvent.setTotalPrice(10000L);
        buyEvent.setItem("java book");
        buyEvent.setBuyEventEnum(BuyEventEnum.PAY);
        ActionEventBus.post(buyEvent);

    }
}
