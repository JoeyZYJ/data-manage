package cn.zyj.platform.controller.web;

import cn.zyj.platform.bean.Weibo;
import cn.zyj.platform.service.WeiboService;
import cn.zyj.platform.utils.ExcelUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-26
 */
@Controller
@RequestMapping("/util")
public class ExportController {

  @Autowired
  WeiboService weiboService;

  private final String[] columnName = {"序号","帐号","密码","状态","时间","是否使用"};
  private final String title = "微博帐号信息";

  @GetMapping("/export")
  @ResponseBody
  public String export(HttpServletRequest request,HttpServletResponse response, @RequestParam(defaultValue = "1") Integer pageNum,
    @RequestParam(defaultValue = "5000") Integer pageSize, @RequestParam(defaultValue = "0") String day,
    String unbanned) {
    final String userAgent = request.getHeader("USER-AGENT");
    PageHelper.startPage(pageNum, pageSize);
    List<Weibo> weiboLists = weiboService.getWeiboLists(Integer.valueOf(day), Integer.valueOf(unbanned));
    PageInfo pageInfo = new PageInfo<>(weiboLists, pageSize);
    List<Weibo> list = pageInfo.getList();
    XSSFWorkbook workbook = new XSSFWorkbook();
    Integer pages = pageInfo.getPages();
    List<Object[]> dataList;
    for (int i=0;i<pages;i++){
      int from;
      int to;
      if (i==0){
        from = i + 1;
        to = list.size();
        dataList = getExcelDataFromWeiboList(from,list);
        ExcelUtils.getXSSFWorkbook(i,title,from + "~" + to + "条",columnName,dataList,workbook);
      }else {
        from = i*pageSize+1;
        PageHelper.startPage(pageNum+1, pageSize);
        List<Weibo> weiboListsAthers = weiboService.getWeiboLists(Integer.valueOf(day), Integer.valueOf(unbanned));
        PageInfo pageInfoAther = new PageInfo<>(weiboListsAthers, pageSize);
        List<Weibo> lists = pageInfoAther.getList();
        to = from + lists.size() - 1;
        dataList = getExcelDataFromWeiboList(from,lists);
        ExcelUtils.getXSSFWorkbook(i,title,from + "~" + to + "条",columnName,dataList,workbook);
      }
    }
    ExcelUtils.doExport(response,userAgent,title,workbook);
    return null;
  }

  private List<Object[]> getExcelDataFromWeiboList(int from, List<Weibo> weiboList) {
    List<Object[]> dataList = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (null != weiboList && weiboList.size() != 0) {
      for (int i = from - 1; i < (from + weiboList.size() - 1); i++) {
        Weibo weibo = weiboList.get(i - from + 1);
        Object[] o = new Object[6];
        o[0] = i + 1;
        o[1] = weibo.getUserName();
        o[2] = weibo.getPassWord();
        String statustr;
        Integer status = weibo.getStatus();
        switch (status) {
          case 0:
            statustr = "正常";
            break;
          case 1:
            statustr = "封禁";
            break;
          case 2:
            statustr = "登录超时";
            break;
          case 3:
            statustr = "密码错误";
            break;
          default:statustr = "正常";
        }
        o[3] = statustr;
        o[4] = formatter.format(weibo.getCreateTime());
        o[5] = weibo.getUsed()==0?"未使用":"已使用";
        dataList.add(o);
      }
    }
    return dataList;
  }

}
