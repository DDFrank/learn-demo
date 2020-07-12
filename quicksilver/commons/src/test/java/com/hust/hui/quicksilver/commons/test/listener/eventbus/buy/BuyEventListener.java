package com.hust.hui.quicksilver.commons.test.listener.eventbus.buy;

import com.google.common.eventbus.Subscribe;
import com.hust.hui.quicksilver.commons.listener.ActionEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by yihui on 2017/3/2.
 */
@Component
public class BuyEventListener {

    private static final Logger logger = LoggerFactory.getLogger(BuyEventListener.class);

    @PostConstruct
    public void init() {
        ActionEventBus.register(this);
    }


    @Subscribe
    public void invoke(BuyEvent args) {
        System.out.println("buyEvent..." + args);
        if (args.getBuyEventEnum() == BuyEventEnum.CANCEL) {
            logger.info("{} buy {} cost: {}", args.getBuyerUser(), args.getItem(), args.getTotalPrice());
        } else {
            logger.info("{} cancelled buy {}", args.getBuyerUser(), args.getItem());
        }
    }
}
