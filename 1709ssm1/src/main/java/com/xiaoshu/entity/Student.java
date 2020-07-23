package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Student implements Serializable {
    @Id
    private Integer sid;

    private String sname;

    private String sex;

    private String img;

    private String hobby;
@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private Integer cid;
@Transient
    private Clazz clazz;
@Transient
@DateTimeFormat(pattern="yyyy-MM-dd")
private String start;
@Transient
@DateTimeFormat(pattern="yyyy-MM-dd")
private String end;
public Integer getSid() {
	return sid;
}
public void setSid(Integer sid) {
	this.sid = sid;
}
public String getSname() {
	return sname;
}
public void setSname(String sname) {
	this.sname = sname;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}
public String getHobby() {
	return hobby;
}
public void setHobby(String hobby) {
	this.hobby = hobby;
}
public Date getBirthday() {
	return birthday;
}
public void setBirthday(Date birthday) {
	this.birthday = birthday;
}
public Integer getCid() {
	return cid;
}
public void setCid(Integer cid) {
	this.cid = cid;
}
public Clazz getClazz() {
	return clazz;
}
public void setClazz(Clazz clazz) {
	this.clazz = clazz;
}
public String getStart() {
	return start;
}
public void setStart(String start) {
	this.start = start;
}
public String getEnd() {
	return end;
}
public void setEnd(String end) {
	this.end = end;
}
@Override
public String toString() {
	return "Student [sid=" + sid + ", sname=" + sname + ", sex=" + sex + ", img=" + img + ", hobby=" + hobby
			+ ", birthday=" + birthday + ", cid=" + cid + ", clazz=" + clazz + ", start=" + start + ", end=" + end
			+ "]";
}

   
}