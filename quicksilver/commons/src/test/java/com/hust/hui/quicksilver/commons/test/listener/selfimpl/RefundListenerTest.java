package com.hust.hui.quicksilver.commons.test.listener.selfimpl;

import org.junit.Test;

/**
 * Created by yihui on 2017/3/8.
 */
public class RefundListenerTest {

    @Test
    public void testRefund() {
        RefundManager refundManager = new RefundManager();

        refundManager.register(new ApplyRefundListener());
        refundManager.register(new RejectRefundListener());

        refundManager.applyNotify(123342L, "申请哒哒哒");
        refundManager.rejectNotify(123342L, "拒绝啦啦啦");
    }

}
