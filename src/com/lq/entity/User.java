package com.lq.entity;
import javax.persistence.*;
@Entity
@Table(name = "t_user")
public class User {

	//用户的手机号：这是主键，充分利用了手机号不可重复的特点
	@Id
	@Column(length = 32)
	private String tel;
	
	//昵称或姓名：用户可以设置也可以不设置。
	@Column(length = 32)
	private String username;
	
	//用户年级:可以设置也可以不设置
	@Column(length = 32)
	private String grade;
	
	//用户性别:可以设置也可以不设置
	@Column(length = 32)
	private String sex;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getOut_book() {
		return out_book;
	}

	public void setOut_book(String out_book) {
		this.out_book = out_book;
	}

	public String getIn_book() {
		return in_book;
	}

	public void setIn_book(String in_book) {
		this.in_book = in_book;
	}

	//已出手的书:采用子表存储，子表只有一个字段，即书的序号。此字段包括用户租出、卖出、报废、尚未租出或卖出的书。
	@Column(length = 32)
	private String out_book;
	
	//正持有的书:采用子表存储，子表只有一个字段，即书的序号。此字段包括用户正在租用的书、租用到期但还没有下家的书、买来的书。
	@Column(length = 32)
	private String in_book;
	

	
}