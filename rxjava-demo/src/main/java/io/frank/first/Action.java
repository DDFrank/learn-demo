package io.frank.first;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jinjunliang
 **/
public class Action {
    public static class A1 {
        // deferred-dependent

        /**
         * 此例演示 如何处理延迟依赖
         *
         */
        public static void main(String[] args) {
            AtomicInteger atomicInteger = new AtomicInteger();

            Observable
                    .range(1, 10)
                    // 当 next触发时触发时执行一些操作
                    .doOnNext(ignored -> atomicInteger.incrementAndGet())
                    // 忽略所有的onNext
                    .ignoreElements()
                    // 在流中假如一个数据
                    .andThen(Single.just(atomicInteger.get()))
                    // 打印
                    .subscribe(System.out::println);

            Observable.range(1, 10).concatWith(Single.defer(() -> Single.just(2)))
                    .subscribe(System.out::println, Throwable::printStackTrace);
        }
    }
}
