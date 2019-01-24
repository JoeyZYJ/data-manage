package cn.zyj.platform.controller.api;

import cn.zyj.platform.bean.Advertise;
import cn.zyj.platform.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
@Controller
@RequestMapping("/ad")
public class AdvertiseController {

  @Autowired
  AdvertiseService advertiseService;


  @RequestMapping("/get")
  @ResponseBody
  public String getAdvertiseListsByMark(String data){
    if (StringUtils.isEmpty(data)){
      return "error : param is empty";
    }else {
      Advertise advertise = advertiseService.getAdvertiseListsByMark(data);
      if (!StringUtils.isEmpty(advertise)){
        return advertise.getDetail();
      }else {
        return "error : data is empty";
      }

    }

  }

}
