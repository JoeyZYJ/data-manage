package cn.zyj.platform.service;

import cn.zyj.platform.bean.TikTok;
import cn.zyj.platform.common.StatusEnum;
import cn.zyj.platform.mapper.TikTokMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-18
 */
@Service
@Transactional
public class TikTokService {
  @Autowired
  TikTokMapper tikTokMapper;

  /**
   * 获取抖音ID
   * 获取后修改此ID的状态为"已使用"
   *
   * @return TikTok
   */
  public TikTok getTikTok() {
    TikTok tikTok = tikTokMapper.selectTikTok();
    if (!StringUtils.isEmpty(tikTok) && tikTok.getUsed() == 0) {
      tikTokMapper.updateTikTokUsedByTikTokId(new TikTok(tikTok.getId(), StatusEnum.USED.getNum()));
    }
    return tikTok;
  }

  public List<TikTok> getTikTokList() {
    return tikTokMapper.selectTikTokList();
  }

  /**
   * 保存抖音ID
   *
   * @param tId 单个抖音ID串
   */
  public void addTikTok(String tId) {
    Long count = tikTokMapper.selectTikTokByTId(tId);
    if (count == 0L) {
      tikTokMapper.insertTikTok(
        new TikTok(tId, StatusEnum.NOMAL.getNum(), new Date(), StatusEnum.TURNON.getNum(), StatusEnum.UNUSE.getNum()));
    }
  }

  /**
   * 删除抖音ID
   *
   * @param id 主键ID
   */
  public void deleteTikTokById(Integer id) {
    tikTokMapper.deleteTikTok(id);
  }

  /**
   * 修改抖音ID状态
   *
   * @param tikTokId 抖音ID
   * @param status 状态
   */
  public void updateTikTokStatusByTikTokId(String tikTokId, Integer status) {
    tikTokMapper.updateTikTokStatusByTikTokId(new TikTok(tikTokId, status, null, null, null));
  }

  public void insertTikTokOnBatch(List<TikTok> tikTokList) {
    tikTokList.forEach(tikTok -> {
      Long count = tikTokMapper.selectTikTokByTId(tikTok.gettId());
      if (count == 0L){
        tikTokMapper.insertTikTok(tikTok);
      }else {
        tikTokMapper.updateTikTokByTikTokId(tikTok);
      }
    });
  }

  public void updateTikTokInfo(TikTok tikTok){
    tikTokMapper.updateTikTok(tikTok);
  }

  public void updateTikTokStatus(Integer turnOff) {
    tikTokMapper.updateTikTokStatus(turnOff);
  }
}
