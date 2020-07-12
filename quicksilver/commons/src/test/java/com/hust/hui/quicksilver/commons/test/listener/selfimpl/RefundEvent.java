package com.hust.hui.quicksilver.commons.test.listener.selfimpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.EventObject;

/**
 * Created by yihui on 2017/3/8.
 */
@Getter
@Setter
@ToString
public class RefundEvent extends EventObject {


    private long orderId;

    private int time;

    private String reason;

    private String state;


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public RefundEvent(Object source) {
        super(source);
    }
}
