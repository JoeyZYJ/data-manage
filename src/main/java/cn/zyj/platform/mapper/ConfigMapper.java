package cn.zyj.platform.mapper;

import cn.zyj.platform.bean.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-20
 */
@Mapper
@Component(value = "configMapper")
public interface ConfigMapper {

  @Select("select * from config where type=#{type} and status=0")
  public List<Config> selectConfig(@Param("type") Integer type);
}
