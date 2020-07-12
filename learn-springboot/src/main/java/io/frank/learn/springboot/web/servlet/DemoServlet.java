package io.frank.learn.springboot.web.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jinjunliang
 **/
public class DemoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("hello servlet");
    }

    public static class DemoServletRegistryBean extends ServletRegistrationBean<DemoServlet> {
        public DemoServletRegistryBean(DemoServlet servlet, String... urlMappings) {
            super(servlet, urlMappings);
        }
    }
}
