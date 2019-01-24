package cn.zyj.platform.common;

/**
 * 通用状态枚举类
 * 0：正常，
 * 1：封禁，
 * 2：登录超时，
 * 3：密码错误，
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-19
 */
public enum StatusEnum {

  NOMAL("正常",0),BANNED("封禁",1),TIMEOUT("登录超时",2),PASSWORDERROR("密码错误",3),UNUSE("未使用",0),USED("已使用",1),TURNON("开启",0),TURNOFF("关闭",1);
  private String title;
  private Integer num;

  private StatusEnum(String title,Integer num){
    this.num = num;
    this.title = title;
  }

  @Override
  public String toString() {
    return this.num+"_"+this.title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }
}
