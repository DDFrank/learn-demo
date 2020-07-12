package com.hust.hui.quicksilver.concurrent.synchronize.copier;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yihui on 2017/12/17.
 */
@ToString
public class DestB {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int age;

    public DestB(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public DestB() {
    }

}
