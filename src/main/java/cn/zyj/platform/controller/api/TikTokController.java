package cn.zyj.platform.controller.api;

import cn.zyj.platform.bean.TikTok;
import cn.zyj.platform.service.TikTokService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalTime;
import java.util.UUID;


/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-18
 */
@Controller
@RequestMapping("/tiktok")
public class TikTokController {

  @Autowired
  TikTokService tikTokService;

  /**
   * 获取抖音ID接口
   * @return string
   */
  @RequestMapping("/get")
  @ResponseBody
  public String getTikTok(){
    TikTok tikTok = tikTokService.getTikTok();
    if (StringUtils.isEmpty(tikTok)){
      return "error : data is empty";
    }else if (tikTok.getUsed()==1){
      return "The count is used!";
    }
    return tikTok.gettId();
  }

  /**
   * 保存抖音ID接口
   * @param data 单个ID串
   * @return string
   */
  @RequestMapping("/add")
  @ResponseBody
  public String addTikTok(String data){
    if (StringUtils.isEmpty(data)){
      return "error : parameter error!";
    }
    tikTokService.addTikTok(data);
    return "success";
  }

  /**
   * 修改抖音ID状态接口
   * @param data 参数
   * @return string
   */
  @RequestMapping("/upd")
  @ResponseBody
  public String updateTikTok(String data){
    if (StringUtils.isEmpty(data)&&!data.contains("_")) {
      return "error : parameter error!";
    }
    String[] param = data.split("_");
    tikTokService.updateTikTokStatusByTikTokId(param[0],Integer.valueOf(param[1]));
    return "success";
  }

  @RequestMapping("/addTest")
  @ResponseBody
  public String test(Integer num){
    num = num==0?1000:num;
    System.out.println(LocalTime.now());
    Long start = System.currentTimeMillis();
    for (int i=0;i<num;i++){
      String uuid = UUID.randomUUID().toString().replace("-","").toLowerCase();
      tikTokService.addTikTok(uuid);
    }
    Long end = System.currentTimeMillis();
    return LocalTime.now()+",totol: "+(end-start);
  }

  @RequestMapping("/getTest")
  @ResponseBody
  public String getTest(Integer num){
    num = num==0?2000:num;
    System.out.println(LocalTime.now());
    Long start = System.currentTimeMillis();
    for (int i=0;i<num;i++){
      tikTokService.getTikTok();
    }
    Long end = System.currentTimeMillis();
    return LocalTime.now()+",totol: "+(end-start);
  }
}
