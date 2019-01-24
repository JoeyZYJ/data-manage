package cn.zyj.platform.service;

import cn.zyj.platform.bean.Config;
import cn.zyj.platform.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-20
 */
@Service
@Transactional
public class ConfigService {
  @Autowired
  ConfigMapper configMapper;
  public List<Config> getConfigByType(Integer type){
    return configMapper.selectConfig(type);
  }
}
