package cn.zyj.platform.service;

import cn.zyj.platform.bean.User;
import cn.zyj.platform.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-25
 */
@Service
@Transactional
public class UserService {

  @Autowired
  UserMapper userMapper;

  public User selectUser(User user){
    return userMapper.selectUserByUserNameAndPassWord(user);
  }

}
