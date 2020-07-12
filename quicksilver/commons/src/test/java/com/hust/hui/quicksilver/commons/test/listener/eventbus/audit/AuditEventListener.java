package com.hust.hui.quicksilver.commons.test.listener.eventbus.audit;

import com.google.common.eventbus.Subscribe;
import com.hust.hui.quicksilver.commons.listener.ActionEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 初审 & 复审的监听器
 * <p/>
 * Created by yihui on 2017/3/1.
 */
@Component
public class AuditEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AuditEventListener.class);


    /**
     * 注册事件
     */
    @PostConstruct
    public void init() {
        ActionEventBus.register(this);
    }


    /**
     * 审核完成后,会发送一条消息
     * <p/>
     * 1. 通过审核
     * 2. 拒绝审核
     * 3. 重新审核
     * <p/>
     * 根据消息, 做出不同的action
     *
     * @param args
     */
    @Subscribe
    public void invoke(AuditEvent args) {
        System.out.println("AuditEvent...." + args);
        if (args.getAuditResultEnum() == AuditEvent.AuditEventEnum.ACCEPT) {
            logger.info("审核通过!!!! {}", args.getRecord());
        } else if (args.getAuditResultEnum() == AuditEvent.AuditEventEnum.REJECT) {
            logger.info("审核拒绝!!!! {}", args.getRecord());
        } else {
            logger.info("重新审核!!!! {}", args.getRecord());
        }

    }
}
