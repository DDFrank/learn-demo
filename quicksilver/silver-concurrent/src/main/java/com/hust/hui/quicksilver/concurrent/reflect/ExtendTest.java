package com.hust.hui.quicksilver.concurrent.reflect;

/**
 * Created by yihui on 2017/11/10.
 */
public class ExtendTest {

    interface ITest {
    }

    abstract class ATest {
        abstract public void print();
    }

    class TestClz extends ATest implements ITest {
        @Override
        public void print() {
            System.out.println("TestClz");
        }
    }


    class A<T, ID> {
    }

    class B extends A<String, Integer> {
    }


    public static void main(String[] args) {

        System.out.println(B.class.getGenericSuperclass());
    }
}
