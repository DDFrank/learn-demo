package com.frank.netty.im.demo.callback;

/**
 * Package com.frank.netty.im.demo.callback
 * Description: ${todo}
 * author 016039
 * date 2018/11/19下午8:26
 */
public class Worker {
    public void doWork() {
        Fetcher fetcher = new MyFetcher(new Data(1, 0));
        // 本例子中是同步的，但是如果将fetchData 移动到别的线程执行，那就是比较典型的异步回调了
        fetcher.fetchData(new FetcherCallback() {
            @Override
            public void onData(Data data) throws Exception {
                System.out.println("Data received: " + data);
            }

            @Override
            public void onError(Throwable cause) {
                System.out.println("An error accour:" + cause.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.doWork();
    }
}
