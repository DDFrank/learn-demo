package io.frank.servicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Package io.frank.servicezuul.filter
 * Description: ${todo}
 * author 016039
 * date 2019/3/2上午8:09
 */
@Component
public class MyFilter extends ZuulFilter{
  /*
  * 返回一个字符串代表过滤器的类型
  * pre: 路由之前
  * routing: 路由之时
  * post: 路由之后
  * error: 发生错误调用
  * */
  @Override
  public String filterType() {
    return "pre";
  }

  /*
  * 在过滤链之中的顺序
  * */
  @Override
  public int filterOrder() {
    return 0;
  }

  /*
  * 是否过滤的逻辑
  * */
  @Override
  public boolean shouldFilter() {
    return true;
  }

  /*
  * 过滤器的具体逻辑
  * */
  @Override
  public Object run() throws ZuulException {
    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest request = context.getRequest();
    System.out.println(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
    Object accessToken = request.getParameter("token");
    if (accessToken == null) {
      context.setSendZuulResponse(false);
      context.setResponseStatusCode(401);
      try {
        context.getResponse().getWriter().write("token is empty");
      } catch (Exception e) {

      }
      return null;
    }
    System.out.println("ok");
    return null;
  }
}
