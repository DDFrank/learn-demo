package com.hust.hui.quicksilver.commons.test.listener.eventbus.audit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yihui on 2017/3/1.
 */
@ToString
@Getter
@Setter
public class AuditEvent {

    /**
     * 审核人
     */
    private String auditUser;


    /**
     * 审核记录
     */
    private String record;


    /**
     * 审核结果
     */
    private AuditEventEnum auditResultEnum;


    public enum AuditEventEnum {

        ACCEPT,

        REJECT,

        RETRY;

    }
}
