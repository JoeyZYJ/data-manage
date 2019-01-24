package cn.zyj.platform.filter;

import cn.zyj.platform.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 * web拦截器
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-22
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //登录不做拦截
    if(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/index"))
    {
      return true;
    }
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() + "/login");
      return false;
    }
    return true;
  }

}
