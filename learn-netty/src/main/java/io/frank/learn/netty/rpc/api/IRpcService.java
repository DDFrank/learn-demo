package io.frank.learn.netty.rpc.api;

/**
 * @author jinjunliang
 **/
public interface IRpcService {
    int add(int a, int b);

    int sub(int a, int b);

    int mul(int a, int b);

    int div(int a, int b);

}
