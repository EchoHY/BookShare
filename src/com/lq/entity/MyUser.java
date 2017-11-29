package com.lq.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_myuser")
public class MyUser {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String Id;
	@Column(length = 32)
	private String nickName;
	
	@Column(length = 32)
	private String password;
	
	@Column(length = 32)
	private String Eaddress;
	
	@Column(length = 32)
	private String Qnumber;
	
	@Column(length = 32)
	private String cellphone;

	@Column(length = 32)
	private String langs;
	
	@Column(length = 32)
	private String sex;
	
	@Column(length = 32)
	private String states;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getLangs() {
		return langs;
	}
	public void setLangs(String langs) {
		this.langs = langs;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getEaddress() {
		return Eaddress;
	}
	public String getQnumber() {
		return Qnumber;
	}
	public void setEaddress(String eaddress) {
		this.Eaddress = eaddress;
	}
	public void setQnumber(String qnumber) {
		this.Qnumber = qnumber;
	}

}
