package cn.zyj.platform.controller.web;

import cn.zyj.platform.bean.User;
import cn.zyj.platform.service.UserService;
import cn.zyj.platform.utils.MD5Utils;
import org.apache.catalina.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
@Controller
public class LoginController {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  UserService userService;

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/index")
  public ModelAndView index(ModelAndView modelAndView, String userName, String passWord, HttpServletRequest request) {

    HttpSession session = request.getSession();
    if (StringUtils.isEmpty(userName)) {
      modelAndView.addObject("userNameError", "帐号不能为空，请重新输入！");
      modelAndView.setViewName("login");
      modelAndView.addObject("passWord", passWord);
      return modelAndView;
    }
    if (StringUtils.isEmpty(passWord)) {
      modelAndView.addObject("passWordError", "密码不能为空，请重新输入！");
      modelAndView.setViewName("login");
      modelAndView.addObject("userName", userName);
      return modelAndView;
    }
    String pw = MD5Utils.md5(passWord);
    User user = new User(userName, pw);
    User u = userService.selectUser(user);
    if (StringUtils.isEmpty(u)){
      modelAndView.addObject("pwerror", "帐号或密码错误，请重新输入！");
      modelAndView.setViewName("login");
      return modelAndView;
    }else {
      session.setAttribute("user", u);
      String basePath =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
          + "/";
      session.setAttribute("basePath", basePath);
      modelAndView.setViewName("index");
      logger.info("login user is ==>{}",u.toString());
      return modelAndView;
    }

  }

  @RequestMapping("/logout")
  public String logOut(HttpServletRequest request) {
    request.getSession().removeAttribute("user");
    return "redirect:/login";
  }
}
