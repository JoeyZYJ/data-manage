package cn.zyj.platform.controller.web;

import cn.zyj.platform.bean.Advertise;
import cn.zyj.platform.bean.Weibo;
import cn.zyj.platform.service.AdvertiseService;
import cn.zyj.platform.service.WeiboService;
import cn.zyj.platform.utils.MyStringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * 广告管理增删改查
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
@Controller
@RequestMapping("/adv")
public class AdvertiseManageController {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  AdvertiseService advertiseService;

  @RequestMapping("/list")
  public String advertiseLists(Map<String, Object> map, @RequestParam(defaultValue = "1") Integer pageNum,
    @RequestParam(defaultValue = "14") Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Advertise> advLists = advertiseService.getAdvertiseLists();
    PageInfo pageInfo = new PageInfo<>(advLists, pageSize);
    map.put("page",pageInfo);
    logger.debug("select advertise list success ==> pageTotal : {} 条", pageInfo.getTotal());
    return "ad_list";
  }

  @PostMapping("/add")
  @ResponseBody
  public String addAdvertise(Advertise advertise) {
    if (!StringUtils.isEmpty(advertise) && !"".equals(advertise.getDetail()) && !"".equals(advertise.getPhoneMark())) {
      List<Advertise> lists = createParams(advertise);
      advertiseService.addAdvertise(lists);
      logger.debug("addAdvertise() success Advertise is {}",advertise.toString());
      return "ok";
    } else {
      logger.debug("add Advertise failure ，the advertise content is null or phone mark is null!");
      return "no";
    }
  }

  @PostMapping("/upd")
  @ResponseBody
  public String updateAdvertise(Advertise advertise) {

    if (!StringUtils.isEmpty(advertise) && !"".equals(advertise.getDetail())&&null != advertise.getId()) {
      if (advertise.getPhoneMark().contains("、")){
        return "no";
      }
      String detail = MyStringUtils.replaceMultipleLineToOne(advertise.getDetail().trim());
      advertise.setDetail(detail);
      advertiseService.updateAdvertiseById(advertise);
      logger.debug("advertise update do success, the object is {}",advertise.toString());
      return "ok";
    } else {
      logger.debug("update Advertise failure ，the advertise id is null or the content is null !");
      return "no";
    }
  }

  @PostMapping("/del")
  @ResponseBody
  public String dropAdvertiseById(Advertise advertise) {
    if (null != advertise && null != advertise.getPhoneId()) {
      String[] ids = advertise.getPhoneId().split(",");
      for (String id : ids){
        advertiseService.deleteAdvertiseById(Integer.valueOf(id));
      }

      logger.debug("advertise update do success , the object id is {},delete time is {}",advertise.getId(),advertise.getCreateTime());
      return "ok";
    } else {
      logger.debug("delete Advertise failure ，the advertise id is null !");
      return "on";
    }
  }

  public List<Advertise> createParams(Advertise advertise) {
    String details = MyStringUtils.replaceMultipleLineToOne(advertise.getDetail().trim());
    String mark = MyStringUtils.replaceMultipleLineToOne(advertise.getPhoneMark());
    String[] detail = details.split("\n");
    String[] marks = mark.split(",");
    List<Advertise> lists = new ArrayList<>();
    Arrays.stream(marks).forEach(m -> {
      Arrays.stream(detail).forEach(d -> {
        Advertise adv = new Advertise(d, "0", m, 0, new Date(), advertise.getRemarks());
        lists.add(adv);
      });
    });
    return lists;
  }

}
