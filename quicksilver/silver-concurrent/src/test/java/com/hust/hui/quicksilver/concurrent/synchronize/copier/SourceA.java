package com.hust.hui.quicksilver.concurrent.synchronize.copier;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yihui on 2017/12/17.
 */
@ToString
public class SourceA {

    @Getter
    @Setter
    private String name;

    @Setter
    public int age;

    public SourceA() {
    }

    public SourceA(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
