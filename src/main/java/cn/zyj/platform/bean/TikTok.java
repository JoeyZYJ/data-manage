package cn.zyj.platform.bean;


import java.util.Date;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-18
 */
public class TikTok {

  private Integer id;
  private String tId;
  private Integer status;// 0：正常，1：封禁，2：登录超时，3：密码错误
  private Date createTime;
  private Integer turnOff;// 0:开启，1:关闭
  private Integer used;// 0:未使用，1:已使用

  public TikTok(){
    super();
  }

  public TikTok(Integer id,Integer used){
    super();
    this.id = id;
    this.used = used;
  }

  public TikTok(String tId,Integer status,Date createTime,Integer turnOff,Integer used){
    super();
    this.tId = tId;
    this.createTime = createTime;
    this.status = status;
    this.turnOff = turnOff;
    this.used = used;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String gettId() {
    return tId;
  }

  public void settId(String tId) {
    this.tId = tId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getTurnOff() {
    return turnOff;
  }

  public void setTurnOff(Integer turnOff) {
    this.turnOff = turnOff;
  }

  public Integer getUsed() {
    return used;
  }

  public void setUsed(Integer used) {
    this.used = used;
  }

  @Override
  public String toString(){
    return "TikTok [id="+id+",tId="+tId+",status="+status+",createTime="+createTime+",turnOff="+turnOff+",used="+used+"]";
  }
}
