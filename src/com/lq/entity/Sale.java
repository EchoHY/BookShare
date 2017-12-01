package com.lq.entity;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_sale")
public class Sale {

	//序号：当书被卖出时，该书的记录从表一移动到此表，序号沿用原序号。
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

	//原主人（的手机号）:从表一复制
	@Column(length = 32)
	private String former_tel;
	
	//现主人（的手机号）:即买此书的人，交易达成时生成此信息。
	@Column(length = 32)
	private String now_tel;
	
	
	//售价:从表一复制。
	@Column(length = 32)
	private BigDecimal sale_price;


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


	public String getFormer_tel() {
		return former_tel;
	}


	public void setFormer_tel(String former_tel) {
		this.former_tel = former_tel;
	}


	public String getNow_tel() {
		return now_tel;
	}


	public void setNow_tel(String now_tel) {
		this.now_tel = now_tel;
	}


	public BigDecimal getSale_price() {
		return sale_price;
	}


	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}
	
	
}