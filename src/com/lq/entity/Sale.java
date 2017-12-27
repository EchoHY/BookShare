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
	private int id;	
	//书的图片:第一次租借时，从上表复制。之后再变换主人时，由上一个主人拍照上传，这么做有利于体现书的新旧程度。
	@Column(length = 64)
	private String picture;
	//书的信息:从上表复制
	@Column(length = 32)
	private String information;
	//原主人（的手机号）:从上表复制
	@Column(length = 32)
	private String origin_openid;
	//上线时间:原主人将此书提交到平台上的时间
	private long start_time;	
	//原交易方式
	private int way;
	//原租价:从上表复制，设置次字段为的是，当首位买者反悔时，书目从此表移动到
	//上表，不至于出现信息丢失的情况
	@Column(length = 32)
	private BigDecimal rent_price;
	//售价:从上表复制
	@Column(length = 32)
	private BigDecimal sale_price;
	//交易是否确定
	private int sureornot;

	public Sale(){}
	public Sale(int id,String picture,String information,
			String origin_openid,long start_time,int way,BigDecimal rent_price,BigDecimal sale_price){
		this.id = id;
		this.picture = picture;
		this.information = information;
		this.origin_openid = origin_openid;
		this.start_time = start_time;
		this.way = way;
		this.rent_price = rent_price;
		this.sale_price = sale_price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getOrigin_openid() {
		return origin_openid;
	}
	public void setOrigin_openid(String origin_openid) {
		this.origin_openid = origin_openid;
	}
	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	public int getWay() {
		return way;
	}
	public void setWay(int way) {
		this.way = way;
	}
	public BigDecimal getRent_price() {
		return rent_price;
	}
	public void setRent_price(BigDecimal rent_price) {
		this.rent_price = rent_price;
	}
	public BigDecimal getSale_price() {
		return sale_price;
	}
	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}
	public int getSureornot() {
		return sureornot;
	}
	public void setSureornot(int sureornot) {
		this.sureornot = sureornot;
	}
	
	
}