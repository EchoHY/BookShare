package com.lq.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_rented")
public class Rented {

	//序号：当书被租出时，该书的记录从上表移动到此表，序号沿用原序号。当书被报废时，所在记录从此表移到下表，序号依然沿用。
	@Column(length = 32)
	private String index;
	
	//书名：从上表复制
	@Column(length = 32)
	private String name;
	
	//书的图片:第一次租借时，从上表复制。之后再变换主人时，由上一个主人拍照上传，这么做有利于体现书的新旧程度。
	@Column(length = 32)
	private String picture;
	
	//书的信息:从上表复制
	@Column(length = 32)
	private String information;


	//原主人（的手机号）:从上表复制
	@Column(length = 32)
	private String former_tel;
	
	//书的现持有者（的手机号）:即租得此书的人，每有一笔租借交易达成时更新此字段
	@Column(length = 32)
	private String now_tel;
	
	//上线时间:原主人将此书提交到平台上的时间
	@Column(length = 32)
	private String start_time;
	
	//开始本次租用的时间:当前租用者开始本次租用的时间
	@Column(length = 32)
	private String begin_time;
	
	//预定租期:租用者在租用时设定的租用时长
	@Column(length = 32)
	private String way;
	
	//租借历史:存储这本书曾被谁租过，以子表形式存储。上一个字段只是当前租这本书的人，本字段是在他之前租这本书的人。这个信息暂定为仅原主人可见。
	@Column(length = 32)
	private String rent_history;
	
	//租价:从上表复制
	@Column(length = 32)
	private BigDecimal rent_price;

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

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getRent_history() {
		return rent_history;
	}

	public void setRent_history(String rent_history) {
		this.rent_history = rent_history;
	}

	public BigDecimal getRent_price() {
		return rent_price;
	}

	public void setRent_price(BigDecimal rent_price) {
		this.rent_price = rent_price;
	}
	
	
}