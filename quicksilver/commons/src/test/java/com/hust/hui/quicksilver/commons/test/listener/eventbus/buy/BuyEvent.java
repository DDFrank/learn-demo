package com.hust.hui.quicksilver.commons.test.listener.eventbus.buy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yihui on 2017/3/2.
 */
@Getter
@Setter
@ToString
public class BuyEvent {


    private BuyEventEnum buyEventEnum;


    /**
     * 购买者
     */
    private String buyerUser;

    /**
     * 总价
     */
    private Long totalPrice;

    /**
     * 数量
     */
    private Integer count;


    /**
     * 商品
     */
    private String item;
}
