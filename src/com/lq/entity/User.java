package com.lq.entity;
import javax.persistence.*;
@Entity
@Table(name = "t_user")
public class User {

	//用户的wx账号：这是主键，充分利用了OPENID不可重复的特点
	@Id
	@Column(length = 32)
	private String id;
	
	@Column(length = 32)
	private String nickname;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(length = 32)
	private String phone;
	
	@Column(length = 32)
	private String grade;
	
	@Column(length = 32)
	private String sex;

}