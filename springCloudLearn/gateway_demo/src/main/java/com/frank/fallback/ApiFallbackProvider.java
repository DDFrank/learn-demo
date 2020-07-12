package com.frank.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 016039
 * @Package com.frank.fallback
 * @Description: ${todo}
 * @date 2018/10/30下午10:05
 */
@Component
public class ApiFallbackProvider implements ZuulFallbackProvider{
    /*
    * 返回要处理错误的服务名
    * */
    @Override
    public String getRoute() {
        return "eurekaClient";
    }

    /**
    * @Description: 返回错误的处理规则
    * @param
    * @return
    * @author 016039
    * @date 2018/10/30 下午10:08
    */
    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "{code:0, message:\"服务器异常!\"}";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(getStatusText().getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
