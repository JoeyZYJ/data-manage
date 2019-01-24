package cn.zyj.platform.mapper;

import cn.zyj.platform.bean.Advertise;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
@Component("advertiseMapper")
public interface AdvertiseMapper {

  @Select("select * from advertise where del_or_not=0 and phone_mark = #{phoneMark} order by rand() LIMIT 1")
  public Advertise selectAdvertiseListsByMark(String phoneMark);

  @Select("select group_concat(t.ids) as phone_id, t.phone_mark, replace(group_concat(t.detail),',','\\n') as detail,substring_index(group_concat(t.detail),',',1) as remarks from (\n" + "  select\n"
    + "    GROUP_CONCAT(id) as ids,detail,\n" + "    GROUP_CONCAT(phone_mark) as phone_mark\n" + "  from advertise\n"
    + "  where del_or_not = 0\n" + "  group by detail\n" + ") as t group by t.phone_mark")
  public List<Advertise> selectAdvertiseLists();

  @Select("insert into advertise(detail,phone_id,phone_mark,status,create_time,remarks) values(#{detail},#{phoneId},#{phoneMark},#{status},#{createTime},#{remarks})")
  public void insertAdvertise(Advertise advertise);

  @Update("update advertise set detail = #{detail},phone_mark = #{phoneMark},remarks = #{remarks} where id = #{id}")
  public void updateAdvertiseById(Advertise advertise);

  /**
   * TODO
   * 是否要做真删除
   *
   * @param id
   */
  @Delete("update advertise set del_or_not=1 where id = #{id}")
  public void deleteAdvertiseById(Integer id);

}
