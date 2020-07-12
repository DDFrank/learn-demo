package com.frank.netty.im.demo.callback;

/**
 * Package com.frank.netty.im.demo.callback
 * Description: 获取的回调
 * author 016039
 * date 2018/11/19下午8:27
 */
public interface FetcherCallback {
    // 接收数据时被调用
    void onData(Data data) throws Exception;
    // 发生错误时调用
    void onError(Throwable cause);
}
