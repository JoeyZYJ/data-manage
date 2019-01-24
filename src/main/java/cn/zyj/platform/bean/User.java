package cn.zyj.platform.bean;

import java.util.Date;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-22
 */
public class User {
  private Integer id;
  private String userName;
  private String passWord;
  private String name;
  private Integer gender;
  private Integer age;
  private Date birthDay;

  public User(){}

  public User(String userName,String passWord){
    this.userName = userName;
    this.passWord = passWord;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Date getBirthDay() {
    return birthDay;
  }

  public void setBirthDay(Date birthDay) {
    this.birthDay = birthDay;
  }

  @Override
  public String toString() {
    return "User [id="+id+",userName="+userName+",passWord="+passWord+",name="+name+"]";
  }
}
