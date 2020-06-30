package com.jiyun.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Stu {

	private Integer sid;
	private String sname;
	private Integer age;
	private String sex;
	
	private Integer depid;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	private String pic;
	
	private Dept dept;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getDepid() {
		return depid;
	}

	public void setDepid(Integer depid) {
		this.depid = depid;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", sname=" + sname + ", age=" + age + ", sex=" + sex + ", depid=" + depid
				+ ", birthday=" + birthday + ", pic=" + pic + "]";
	}
	
}
