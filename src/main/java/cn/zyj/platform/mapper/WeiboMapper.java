package cn.zyj.platform.mapper;

import cn.zyj.platform.bean.Weibo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-18
 */
@Mapper
@Component(value = "weiboMapper")
public interface WeiboMapper {

  @Select("insert into table_weibo(userName,passWord,status,createTime,turnOff) values(#{userName},#{passWord},#{status},#{createTime},#{turnOff})")
  public void insertWeibo(Weibo weibo);

  @Select("update table_weibo set del_or_not=1 where id = #{id}")
  public void deleteWeiboById(Integer id);

  @Select("select * from table_weibo where status=0 and turnOff=0 and used=0 and del_or_not=0 order by createTime asc limit 1")
  public Weibo selectWeibo();

  @Select("<script> select * from table_weibo where 1=1 and del_or_not=0"
    + "<if test='unBunned != 0'> and status !=1 and used=1 </if>"
    + " <when test='day!=0 and day!=16'> and TO_DAYS(now())-TO_DAYS(createTime) = ${day} </when>"
    + " <when test='day!=0 and day==16'> and TO_DAYS(now())-TO_DAYS(createTime) >= ${day} </when>"
    + " order by createTime asc </script>")
  public List<Weibo> selectWeiboLists(@Param("day")Integer day,@Param("unBunned")Integer unBunned);

  @Select("select count(*) from table_weibo where del_or_not=0 and userName=#{userName}")
  public Long selectWeiboByUserName(String userName);

  @Select("update table_weibo set status=#{status},used=#{used},turnOff=#{turnOff} where userName=#{userName}")
  public void updateWeiboByUserName(Weibo weibo);

  @Select("update table_weibo set status=#{status} where userName=#{userName}")
  public void updateWeiboStatusByUserName(Weibo weibo);

  @Select("update table_weibo set used=#{used} where id=#{id}")
  public void updataWeiboById(Weibo weibo);

  @Select("update table_weibo set used=#{used} where userName=#{userName}")
  public void updataWeiboByUserName(Weibo weibo);

  @Select("update table_weibo set turnOff=#{turnOff}")
  public void updataWeiboStatus(Integer turnOff);

  @Select("update table_weibo set userName=#{userName},passWord=#{passWord},used=#{used} where id=#{id}")
  public void updateWeiboInfo(Weibo weibo);
}
