package com.hust.hui.quicksilver.queue.delayqueue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yihui on 2017/10/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailInfo {

    private int itemId;

    private String title;

    private String desc;

    private int price;
}
