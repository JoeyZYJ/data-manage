package cn.zyj.platform.controller.web;

import cn.zyj.platform.bean.Weibo;
import cn.zyj.platform.service.WeiboService;
import cn.zyj.platform.utils.MyStringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *  微博帐号信息管理增删改查
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
@Controller
@RequestMapping("/wb")
public class WeiboManageController {
  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  WeiboService weiboService;

  @RequestMapping("/list")
  public String weiboLists(Map<String, Object> map, @RequestParam(defaultValue = "1") Integer pageNum,
    @RequestParam(defaultValue = "12") Integer pageSize,@RequestParam(defaultValue = "0")String day,@RequestParam(defaultValue = "0")String unbanned) {
    PageHelper.startPage(pageNum, pageSize);
    List<Weibo> weiboLists = weiboService.getWeiboLists(Integer.valueOf(day),Integer.valueOf(unbanned));
    PageInfo pageInfo = new PageInfo<>(weiboLists, pageSize);
    map.put("page",pageInfo);
    map.put("unbanned", "0");
    logger.debug("select weibo info list success ==> pageTotal : {} 条", pageInfo.getTotal());
    return "weibo_list";
  }

  @GetMapping("/ajax")
  @ResponseBody
  public Map<String, Object> weiboListsAjax(@RequestParam(defaultValue = "1") Integer pageNum,
    @RequestParam(defaultValue = "12") Integer pageSize,@RequestParam(defaultValue = "0") String day,String unbanned) {
    Map<String, Object> map = new HashMap<>();
    PageHelper.startPage(pageNum, pageSize);
    List<Weibo> weiboLists = weiboService.getWeiboLists(Integer.valueOf(day),Integer.valueOf(unbanned));
    PageInfo pageInfo = new PageInfo<>(weiboLists, pageSize);
    Integer begin = pageInfo.getPageNum() == 0 ? 1 : 1 + (pageInfo.getPageSize() * (pageInfo.getPageNum() - 1));
    map.put("start", begin);
    map.put("end", pageSize - pageInfo.getSize() == 0 ?
      pageInfo.getPageNum() * pageInfo.getPageSize() :
      (begin + pageInfo.getSize() - 1));
    map.put("weiboLists", pageInfo.getList());
    map.put("pageNum", pageInfo.getPageNum());
    map.put("pageSize", pageInfo.getPageSize());
    map.put("isFirstPage", pageInfo.isIsFirstPage());
    map.put("totalPages", pageInfo.getPages());
    map.put("isLastPage", pageInfo.isIsLastPage());
    map.put("pageTotal", pageInfo.getTotal());
    map.put("ok", "ok");
    map.put("unbanned", day.equals("0")?"0":"1");
    logger.debug("select weibo list success ==> pageTotal : {} 条", pageInfo.getTotal());
    return map;
  }

  @PostMapping("/add")
  @ResponseBody
  public String addWeiboOnBatch(Weibo weibo) {
    if (!StringUtils.isEmpty(weibo.getUserName())){
      String userNameAndPassWordString = MyStringUtils.replaceMultipleLineToOne(weibo.getUserName().trim());
      String[] str = userNameAndPassWordString.split("\n");
      List<Weibo> weiboList = new ArrayList<>();
      Arrays.stream(str).forEach(s -> {
        String[] userNamePassword = s.split("----");
        Weibo weibo1 = new Weibo(userNamePassword[0],userNamePassword[1],0,new Date(),0,0);
        weiboList.add(weibo1);
      });
      weiboService.insertWeiboOnBatch(weiboList);
      logger.debug("add weibo informations success, the weibo info is {}!",weibo.toString());
      return "ok";
    }else {
      logger.debug("add weibo info failure,the userName is null!");
      return "no";
    }
  }

  @PostMapping("/upd")
  @ResponseBody
  public String updateWeibo(Weibo weibo) {
    if (StringUtils.isEmpty(weibo.getUserName())&&StringUtils.isEmpty(weibo.getId())) {
      logger.debug("update weibo info failure, userNameAndPassWordString and id is null");
      return "no";
    } else {
      String[] userNameAndPassWord = weibo.getUserName().split("----");
      if (userNameAndPassWord.length>2){
        logger.debug("update weibo info failure , the info not only!");
        return "nt";
      }else {
        weiboService.updateWeiboInfo(new Weibo(weibo.getId(), userNameAndPassWord[0], userNameAndPassWord[1], 0, new Date()));
        logger.debug("update weibo info success! the userName : {} and the id : {}",userNameAndPassWord[0],weibo.getId());
        return "ok";
      }

    }
  }

  @PostMapping("/del")
  @ResponseBody
  public String dropAdvertiseById(Weibo weibo) {
    if (null != weibo && null != weibo.getId()) {
      weiboService.deleteWeiboByid(weibo.getId());
      logger.debug("delete weibo info success! id is {}", weibo.getId());
      return "ok";
    } else {
      logger.debug("delete weibo info failure ,the id is null");
      return "no";
    }
  }
}
