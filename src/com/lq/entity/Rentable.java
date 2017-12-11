package com.lq.entity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
@Entity
@Table(name = "t_rentable")
public class Rentable {

	//序号：作为表的主键，结构是：原主人手机号+原主人首次提交信息的年月日时分秒，如1871122334420171231120000
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="tableGenerator")
	@TableGenerator(name="tableGenerator",initialValue=0,allocationSize=1)
	@Column(length = 32)
	private int id;
	
	//书名：由书的原主人提交信息时输入或从下表复制
	@Column(length = 32)
	private String name;
	
	//书的图片:由书的原主人提交信息时输入或从下表复制
	@Column(length = 64)
	private String picture;
	
	//书的图片:由书的原主人提交信息时输入或从下表复制
	@Column(length = 64)
	private String picturesec;
	
	
	//书的信息:由书的原主人提交信息时拍摄书的条码上传或从下表复制
	@Column(length = 32)
	private String information;


	//原主人（的手机号）:从书的原主人微信账户中读取
	@Column(length = 32)
	private String former_tel;
	
	//书的现持有者（的手机号）:信息首次提交时，从上一个字段复制；租用到期等待新主人时，从下表复制
	@Column(length = 32)
	private String now_tel;
	
	//上线时间:原主人将此书提交到平台上的时间
	@Column(length = 32)
	private String start_time;
	
	//交易方式:即书的原主人希望这本书被租出还是卖出（亦或两者均可），由书的原主人提交信息时输入
	@Column(length = 32)
	private String way;
	
	//租价:由书的原主人提交信息时输入，若只想卖，则此项设为空。单位是固定的。即“元/天”，采用Decimal数据类型存储
	@Column(length = 32)
	private BigDecimal rent_price;
	
	//售价:由书的原主人提交信息时输入，若只想租，则此项设为空。单位是固定的。即“元”，采用Decimal数据类型存储。
	@Column(length = 32)
	private BigDecimal sale_price;
	
	//租借历史:若此书是租用到期后等待下一个主人，则该字段从下表复制，否则为空。
	@Column(length = 32)
	private String rent_history;
	
	//总收入：若此书是租用到期后等待下一个主人，则该字段从下表复制，否则为空。
	@Column(length = 32)
	private BigDecimal income;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
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
	
	public String getPicturesec() {
		return picturesec;
	}

	public void setPicturesec(String picturesec) {
		this.picturesec = picturesec;
	}

}