package com.frank.netty.im.demo.callback;

/**
 * Package com.frank.netty.im.demo.callback
 * Description: ${todo}
 * author 016039
 * date 2018/11/19下午8:30
 */
public class MyFetcher implements Fetcher {

    final Data data;

    public MyFetcher(Data data) {
        this.data = data;
    }

    @Override
    public void fetchData(FetcherCallback callback) {
        try {
            callback.onData(data);
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}
