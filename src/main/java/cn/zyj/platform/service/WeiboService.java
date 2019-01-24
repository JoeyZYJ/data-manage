package cn.zyj.platform.service;

import cn.zyj.platform.bean.Weibo;
import cn.zyj.platform.common.StatusEnum;
import cn.zyj.platform.mapper.WeiboMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-19
 */
@Service
@Transactional
public class WeiboService {

  @Autowired
  WeiboMapper weiboMapper;

  /**
   * 获取微博帐号密码
   * 获取后修改此微博帐号密码的状态为"已使用"
   *
   * @return
   */
  public Weibo getWeibo() {
    Weibo weibo = weiboMapper.selectWeibo();
    if (!StringUtils.isEmpty(weibo) && weibo.getUsed() == 0) {
      weiboMapper.updataWeiboById(new Weibo(weibo.getId(), StatusEnum.USED.getNum()));
    }
    return weibo;
  }

  public List<Weibo> getWeiboLists(Integer day,Integer unBunned) {
    return weiboMapper.selectWeiboLists(day,unBunned);
  }

  /**
   * 保存微博帐号
   *
   * @param weibo
   */
  public void insertWeibo(Weibo weibo) {
    Long count = weiboMapper.selectWeiboByUserName(weibo.getUserName());
    if (count == 0L) {// 如果不存在就保存
      weiboMapper.insertWeibo(weibo);
    }else {// 存在就修改状态为未使用
      weiboMapper.updateWeiboByUserName(weibo);
    }
  }

  /**
   * 批量保存微博帐号密码
   * 没有就保存，存在就update
   *
   * @param lists
   */
  public void insertWeiboOnBatch(List<Weibo> lists) {
    lists.forEach(weibo -> {
      Long count = weiboMapper.selectWeiboByUserName(weibo.getUserName());
      if (count == 0L) {
        weiboMapper.insertWeibo(weibo);
      } else {
        weiboMapper.updateWeiboByUserName(weibo);
      }
    });
  }

  /**
   * 修改微博帐号
   *
   * @param weibo
   */
  public void updateWeiboByUserName(Weibo weibo) {
    weiboMapper.updateWeiboByUserName(weibo);
  }

  public void updateWeiboStatusByUserName(Weibo weibo){
    weiboMapper.updateWeiboStatusByUserName(weibo);
  }

  public void updateWeiboStatus(Integer turnOff) {
    weiboMapper.updataWeiboStatus(turnOff);
  }

  public void updateWeiboInfo(Weibo weibo) {
    weiboMapper
      .updateWeiboInfo(weibo);
  }

  public void deleteWeiboByid(Integer id) {
    weiboMapper.deleteWeiboById(id);
  }

}
