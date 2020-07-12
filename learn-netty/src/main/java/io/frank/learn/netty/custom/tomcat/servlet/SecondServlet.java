package io.frank.learn.netty.custom.tomcat.servlet;

import io.frank.learn.netty.custom.tomcat.GPRequest;
import io.frank.learn.netty.custom.tomcat.GPResponse;
import io.frank.learn.netty.custom.tomcat.GPServlet;

/**
 * @author jinjunliang
 **/
public class SecondServlet extends GPServlet {
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        doPost(request, response);
    }

    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("This is Second Servlet");
    }
}
