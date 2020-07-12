package com.frank.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 016039
 * @Package com.frank.filter
 * @Description: ${todo}
 * @date 2018/10/30下午9:58
 */
@Component
public class ApiFilter extends ZuulFilter {
    /*
    * 选定过滤类型的方法
    *   pre : 路由之前
    *   routing : 路由之时
    *   post : 路由之后
    *   error: 发生错误时调用
    * */
    @Override
    public String filterType() {
        return "pre";
    }

    /*
    * 过滤的顺序，如果有多个过滤器，则数字越小越先执行
    * */
    @Override
    public int filterOrder() {
        return 0;
    }

    /*
    * 表示是否需要过滤，这里可以做逻辑判断
    * */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*
    * 过滤器执行的具体逻辑，可以在这里做权限判断或者合法校验等内容
    * */
    @Override
    public Object run() {
        // 这里写校验代码
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getParameter("token");
        if(!"12345".equals(token)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            try{
                requestContext.getResponse().getWriter().write("token is invalid.");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
