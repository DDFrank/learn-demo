package io.frank.learn.netty.custom.tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @author jinjunliang
 **/
public class GPRequest {
    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public GPRequest(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;

    }

    public String getMethod() {
        return request.method().name();
    }

    public String getUrl() {
        return request.uri();
    }
}
