package io.frank.learn.netty.rpc.protocol;

import java.io.Serializable;

/**
 * 自定义传输协议
 *
 * @author jinjunliang
 **/
public class InvokerProtocol implements Serializable {
    private String className;
    private String methodName;
    // 参数类型
    private Class<?>[] parames;
    //  参数列表
    private Object[] values;
}
