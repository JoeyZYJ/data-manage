package cn.zyj.platform.controller.web;

import cn.zyj.platform.bean.Config;
import cn.zyj.platform.common.StatusEnum;
import cn.zyj.platform.service.ConfigService;
import cn.zyj.platform.service.TikTokService;
import cn.zyj.platform.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-19
 */
@Controller
@RequestMapping("/com")
public class CommandController {

  @Autowired
  TikTokService tikTokService;
  @Autowired
  WeiboService weiboService;
  @Autowired
  ConfigService configService;

  @RequestMapping("/cmd")
  @ResponseBody
  public String cmd(Integer data){
    if (StringUtils.isEmpty(data)){
      tikTokService.updateTikTokStatus(StatusEnum.TURNON.getNum());
      weiboService.updateWeiboStatus(StatusEnum.TURNON.getNum());
    }else{
      tikTokService.updateTikTokStatus(data);
      weiboService.updateWeiboStatus(data);
    }

    return "success";
  }

  @RequestMapping("/list")
  public String urlPage(HttpServletRequest request, Map<String,Object> map){
    String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
    List<Config> lists = configService.getConfigByType(0);
    map.put("url",url);
    map.put("configLists",lists);
    return "api_list";
  }
}
