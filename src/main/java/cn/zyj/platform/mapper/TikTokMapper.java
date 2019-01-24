package cn.zyj.platform.mapper;

import cn.zyj.platform.bean.TikTok;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-18
 */
@Mapper
@Component(value = "tikTokMapper")
public interface TikTokMapper {

  @Select("SELECT * FROM table_tiktok where status=0 and turnOff=0 and used=0 and del_or_not=0 order by createTime asc limit 1")
  public TikTok selectTikTok();

  @Select("SELECT * FROM table_tiktok where del_or_not=0 order by createTime asc")
  public List<TikTok> selectTikTokList();

  @Select("select count(*) from table_tiktok where del_or_not=0 and tId=#{tId}")
  public Long selectTikTokByTId(String tId);

  @Insert("INSERT INTO table_tiktok(tId,createTime,status,turnOff,used) VALUES(#{tId},#{createTime},#{status},#{turnOff},#{used})")
  public void insertTikTok(TikTok tikTok);

  @Update("UPDATE table_tiktok SET tId=#{tId},used=#{used} WHERE id=#{id}")
  public void updateTikTok(TikTok tikTok);

  @Select("update table_tiktok set status=#{status} where tId=#{tId}")
  public void updateTikTokStatusByTikTokId(TikTok tikTok);

  @Select("update table_tiktok set used=#{used} where tId=#{tId}")
  public void updateTikTokByTikTokId(TikTok tikTok);

  @Select("update table_tiktok set used=#{used} where id=#{id}")
  public void updateTikTokUsedByTikTokId(TikTok tikTok);

  @Delete("update table_tiktok set del_or_not=1 WHERE id=#{id}")
  public void deleteTikTok(Integer id);

  @Select("update table_tiktok set turnOff=#{turnOff}")
  public void updateTikTokStatus(Integer turnOff);

}
