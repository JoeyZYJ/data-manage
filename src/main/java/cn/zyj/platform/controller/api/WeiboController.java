package cn.zyj.platform.controller.api;

import cn.zyj.platform.bean.Weibo;
import cn.zyj.platform.common.StatusEnum;
import cn.zyj.platform.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 微博相关接口
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-19
 */
@Controller
@RequestMapping("/weibo")
public class WeiboController {

  @Autowired
  WeiboService weiboService;

  /**
   * 上传微博帐号密码接口
   * @param data 帐号密码，格式为：aaa----bbbbb
   * @return string
   */
  @RequestMapping("/add")
  @ResponseBody
  public String addWeibo(String data){
    if(StringUtils.isEmpty(data)){
      return "error : parameter error!";
    }
    String[] weibo = data.split("----");
    weiboService.insertWeibo(new Weibo(weibo[0],weibo[1],StatusEnum.NOMAL.getNum(),new Date(),StatusEnum.TURNON.getNum(),StatusEnum.UNUSE.getNum()));
    return "success";
  }

  /**
   * 获取微博帐号接口
   * @return 帐号+"----"+密码
   */
  @RequestMapping("/get")
  @ResponseBody
  public String getWeibo(){
    Weibo weibo = weiboService.getWeibo();
    if (StringUtils.isEmpty(weibo)){
      return "data is empty";
    }else if (weibo.getUsed()==1){
      return "The count is used!";
    }
    return weibo.getUserName()+"----"+weibo.getPassWord();
  }

  /**
   * 修改帐号的状态
   * @param data 数据
   * @return string
   */
  @RequestMapping("/upd")
  @ResponseBody
  public String updateWeiboByUserName(String data){
    if (StringUtils.isEmpty(data)&& !data.contains("_")) {
      return "error : parameter error!";
    }
    String[] param = data.split("_");
    weiboService.updateWeiboStatusByUserName(new Weibo(param[0],null,Integer.valueOf(param[1]),null,null,null));
    return "success";
  }
}
