package cn.zyj.platform.service;

import cn.zyj.platform.bean.Advertise;
import cn.zyj.platform.mapper.AdvertiseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
@Service
public class AdvertiseService {

  @Autowired
  AdvertiseMapper advertiseMapper;

  public Advertise getAdvertiseListsByMark(String mark) {
    return advertiseMapper.selectAdvertiseListsByMark(mark);
  }

  public List<Advertise> getAdvertiseLists() {
    return advertiseMapper.selectAdvertiseLists();
  }

  public void addAdvertise(List<Advertise> advertiseList) {
    if (!StringUtils.isEmpty(advertiseList) || null != advertiseList){
      for (Advertise adv : advertiseList) {
        advertiseMapper.insertAdvertise(adv);
      }
    }
  }

  public void updateAdvertiseById(Advertise advertise){
      advertiseMapper.updateAdvertiseById(advertise);
  }

  public void deleteAdvertiseById(Integer id){
    advertiseMapper.deleteAdvertiseById(id);
  }
}
