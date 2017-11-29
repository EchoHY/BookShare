package com.lq.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_reply")
public class Reply {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String id;
	
	@Column(length = 32)
	private String username;
	
	@Column(length = 32)
	private String sex;
	
	@Column(length = 32)
	
	private String stunum;


	@Column(length = 32)
	private String mycard;
	
	@Column(length = 32)
	private String destination;
	
	@Column(length = 32)
	private String organisername;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStunum() {
		return stunum;
	}

	public void setStunum(String stunum) {
		this.stunum = stunum;
	}

	public String getMycard() {
		return mycard;
	}

	public void setMycard(String mycard) {
		this.mycard = mycard;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrganisername() {
		return organisername;
	}

	public void setOrganisername(String organisername) {
		this.organisername = organisername;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	@Column(length = 32)
	private String verification;
}