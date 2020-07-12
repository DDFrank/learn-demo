package com.hust.hui.quicksilver.commons.test.listener.groovy

public class Print$Proxy implements IPrint {

    @Override
    public void print(String content) {
        System.out.println("groovy" + content);
    }
}