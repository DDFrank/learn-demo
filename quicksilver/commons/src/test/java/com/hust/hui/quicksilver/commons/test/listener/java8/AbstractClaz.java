package com.hust.hui.quicksilver.commons.test.listener.java8;

/**
 * Created by yihui on 2017/6/4.
 */
public abstract  class AbstractClaz {

    public static void main(String[] args) {
        String a = "a";
        String s1  = a + "b";
        String s2 = "a" + "b";
        System.out.println(s1 == "ab");
        System.out.println(s2 == "ab");


        System.out.println(get());


        getB().print();




     }


    private static int get() {
        int x = 10;
        try {
            return x;
        } finally {
            return ++x;
        }
    }


    private static B getB() {
        B b = new B();

        try {
            return b;
        } finally {
            b.setS("shasha");
        }
    }
}


class A {

    String s = "hello";
}

class B extends A implements C {

    public void print() {
        System.out.println(super.s);
    }


    public String getS() {
        return super.s;
    }

    public void setS(String str) {
        super.s = str;
    }
}


interface C {
    String s = "world";
}

