package io.frank.first;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author jinjunliang
 **/
public class AutoEmitter {
    public static void main(String[] args) {
        // 自己可以决定如何取发送数据
        Observable.create(emitter -> {
            while (!emitter.isDisposed()) {
                long time = System.currentTimeMillis();
                emitter.onNext(time);
                if (time % 2 != 0) {
                    emitter.onError(new IllegalStateException("Odd millisecond!"));
                    break;
                }
            }
        }).subscribe(System.out::println, Throwable::printStackTrace);
    }
}
