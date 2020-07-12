package com.hust.hui.quicksilver.commons.test.listener.selfimpl;

/**
 * Created by yihui on 2017/3/8.
 */
public class ApplyRefundListener implements RefundListener {
    public void invoke(RefundEvent refundEvent) {
        System.out.println("申请退款了...");
    }
}
