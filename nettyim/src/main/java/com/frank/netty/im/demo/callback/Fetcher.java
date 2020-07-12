package com.frank.netty.im.demo.callback;

/**
 * Package com.frank.netty.im.demo.callback
 * Description: ${todo}
 * author 016039
 * date 2018/11/19下午8:27
 */
public interface Fetcher {
    // 获取数据，传入回调，当获取到数据或者报错时调用
    void fetchData(FetcherCallback callback);
}
