package com.hust.hui.quicksilver.commons.test.listener.selfimpl;

import java.util.EventListener;

/**
 * Created by yihui on 2017/3/8.
 */
public interface RefundListener extends EventListener {

    void invoke(RefundEvent refundEvent);

}
