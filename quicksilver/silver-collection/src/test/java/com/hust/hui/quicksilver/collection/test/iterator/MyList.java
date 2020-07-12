package com.hust.hui.quicksilver.collection.test.iterator;

import java.util.Iterator;

/**
 * Created by yihui on 2017/8/30.
 */
public class MyList<T> implements Iterable<T> {

    private T[] msg;


    public MyList(T... args) {
        msg = args;
    }


    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }


    private int len() {
        return msg.length;
    }


    class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < len();
        }


        @Override
        public T next() {
            return (T) msg[cursor++];
        }
    }
}
