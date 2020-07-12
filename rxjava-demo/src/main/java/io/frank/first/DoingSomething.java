package io.frank.first;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author jinjunliang
 **/
public class DoingSomething {
    public static void main(String[] args) throws Exception {
        // 从某个异步任务中构建
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                // 从io线程 去执行阻塞操作
                .subscribeOn(Schedulers.io())
                // 从单个线程 消费数据
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }


}
