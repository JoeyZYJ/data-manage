package cn.zyj.platform.controller.web;

import cn.zyj.platform.bean.TikTok;
import cn.zyj.platform.bean.Weibo;
import cn.zyj.platform.common.StatusEnum;
import cn.zyj.platform.service.TikTokService;
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
 *  抖音帐号信息管理
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
@Controller
@RequestMapping("/tt")
public class TikTokManageController {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  TikTokService tikTokService;

  @RequestMapping("/list")
  public String tikTokLists(Map<String, Object> map, @RequestParam(defaultValue = "1") Integer pageNum,
    @RequestParam(defaultValue = "12") Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<TikTok> tikTokLists = tikTokService.getTikTokList();
    PageInfo pageInfo = new PageInfo<>(tikTokLists, pageSize);
    map.put("page", pageInfo);
    logger.debug("select tiktok list success ==> pageTotal : {} 条", pageInfo.getTotal());
    return "tiktok_list";
  }

  @PostMapping("/add")
  @ResponseBody
  public String addTikTokOnBatch(TikTok tikTok) {
    if (!StringUtils.isEmpty(tikTok) && !"".equals(tikTok.gettId())) {
      String tids = MyStringUtils.replaceMultipleLineToOne(tikTok.gettId().trim());
      String[] ttid = tids.split("\n");
      List<TikTok> lists = new ArrayList<>();
      Arrays.stream(ttid).forEach(s -> {
        lists.add(
          new TikTok(s, StatusEnum.NOMAL.getNum(), new Date(), StatusEnum.TURNON.getNum(), StatusEnum.UNUSE.getNum()));
      });
      tikTokService.insertTikTokOnBatch(lists);
      logger.debug("add tiktok info success ,the object is {}!",tikTok.toString());
      return "ok";
    } else {
      logger.debug("add tiktok failure,the tId is null!");
      return "no";
    }
  }

  @PostMapping("/upd")
  @ResponseBody
  public String updateTikTokInfo(TikTok tikTok) {
    if (!StringUtils.isEmpty(tikTok) && !"".equals(tikTok.gettId())) {
      String tid = MyStringUtils.replaceMultipleLineToOne(tikTok.gettId().trim());
      tikTok.settId(tid);
      tikTok.setUsed(0);
      tikTokService.updateTikTokInfo(tikTok);
      logger.debug("update tiktok info success,the id is {}!",tikTok.getId());
      return "ok";
    } else {
      logger.debug("update tiktok info failure,the tId and id is null!");
      return "no";
    }
  }

  @PostMapping("/del")
  @ResponseBody
  public String deleteTikTokById(TikTok tikTok) {
    if (null != tikTok && null != tikTok.getId()) {
      tikTokService.deleteTikTokById(tikTok.getId());
      logger.debug("delete tiktok info success! id is {}",tikTok.getId());
      return "ok";
    }else {
      logger.debug("delete tiktok info failure! id is null");
      return "no";
    }
  }
}
