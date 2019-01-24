package cn.zyj.platform.mapper;

import cn.zyj.platform.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-25
 */
@Mapper
@Component(value = "userMapper")
public interface UserMapper {
  @Select("select * from user where user_name = #{userName} and pass_word = #{passWord}")
  public User selectUserByUserNameAndPassWord(User user);
}
