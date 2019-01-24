package cn.zyj.platform.bean;


import java.util.Date;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-21
 */
public class Advertise {
  private Integer id;
  private String detail;
  private String phoneId;
  private String phoneMark;
  private Integer status;
  private Date createTime;
  private String remarks;

  public Advertise(){}

  public Advertise(String detail,String phoneId,String phoneMark,Integer status,Date createTime,String remarks){
    this.detail = detail;
    this.phoneId = phoneId;
    this.phoneMark = phoneMark;
    this.status = status;
    this.createTime = createTime;
    this.remarks = remarks;
  }
  public Advertise(Integer id,String detail,String phoneId,String phoneMark,Integer status,Date createTime,String remarks){
    this.id = id;
    this.detail = detail;
    this.phoneId = phoneId;
    this.phoneMark = phoneMark;
    this.status = status;
    this.createTime = createTime;
    this.remarks = remarks;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getPhoneId() {
    return phoneId;
  }

  public void setPhoneId(String phoneId) {
    this.phoneId = phoneId;
  }

  public String getPhoneMark() {
    return phoneMark;
  }

  public void setPhoneMark(String phoneMark) {
    this.phoneMark = phoneMark;
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

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  @Override
  public String toString(){
    return "Advertise [ id = " + id
          + ",detail = " + detail
          + ",phoneId = " + phoneId
          + ",phoneMark = " + phoneMark
          + ",status = " + status
          + ",createTime = " + createTime
          + ",remarks = " + remarks ;
  }
}
