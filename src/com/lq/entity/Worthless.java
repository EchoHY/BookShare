package com.lq.entity;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_worthless")
public class Worthless {

	//序号：当书被报废时，所在记录从上表移到此表，序号依然沿用。
	@Id
	@Column(length = 32)
	private String index;
	
	//书名：从表一复制
	@Column(length = 32)
	private String name;
	
	//书的图片:从表一复制
	@Column(length = 32)
	private String picture;
	
	//书的信息:从表一复制
	@Column(length = 32)
	private String information;
	
	//报废时间:书被报废时，读取服务器时间
	@Column(length = 32)
	private String worthless_time;

	//原主人（的手机号）:从表一复制
	@Column(length = 32)
	private String former_tel;
	
	//最后一个主人（的手机号）:从表一的“书的现持有者”字段复制
	@Column(length = 32)
	private String last_tel;
	
	
	//租借历史:存储这本书曾被谁租过，以子表形式存储，也包括最后一个主人。
	@Column(length = 32)
	private String rent_history;
	
	//总收入:从表一复制。
	@Column(length = 32)
	private BigDecimal income;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getWorthless_time() {
		return worthless_time;
	}

	public void setWorthless_time(String worthless_time) {
		this.worthless_time = worthless_time;
	}

	public String getFormer_tel() {
		return former_tel;
	}

	public void setFormer_tel(String former_tel) {
		this.former_tel = former_tel;
	}

	public String getLast_tel() {
		return last_tel;
	}

	public void setLast_tel(String last_tel) {
		this.last_tel = last_tel;
	}

	public String getRent_history() {
		return rent_history;
	}

	public void setRent_history(String rent_history) {
		this.rent_history = rent_history;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	
	
}