package com.hust.hui.quicksilver.commons.test.listener.selfimpl;

import java.util.Vector;

/**
 * Created by yihui on 2017/3/8.
 */
public class RefundManager {

    private Vector<RefundListener> vector = new Vector<RefundListener>();


    // 注册
    public void register(RefundListener refundListener) {
        vector.add(refundListener);
    }


    public void applyNotify(long orderId, String reason) {
        RefundEvent refundEvent = new RefundEvent(this);
        refundEvent.setOrderId(orderId);
        refundEvent.setReason(reason);
        refundEvent.setState("apply");
        refundEvent.setTime((int) (System.currentTimeMillis() / 1000));


        for (RefundListener refundListener : vector) {
            if (refundListener instanceof ApplyRefundListener) {
                refundListener.invoke(refundEvent);
            }
        }
    }


    public void rejectNotify(long orderId, String reason) {
        RefundEvent refundEvent = new RefundEvent(this);
        refundEvent.setOrderId(orderId);
        refundEvent.setReason(reason);
        refundEvent.setState("reject");
        refundEvent.setTime((int) (System.currentTimeMillis() / 1000));


        for (RefundListener refundListener : vector) {
            if (refundListener instanceof RejectRefundListener) {
                refundListener.invoke(refundEvent);
            }
        }
    }
}
