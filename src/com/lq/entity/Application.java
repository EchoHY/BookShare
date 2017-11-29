package com.lq.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_application")
public class Application {

	/*
	 * 
	 * 
	 * */
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
	private String tripway;
	
	@Column(length = 32)
	private String triptime;
	
	public String getTriptime() {
		return triptime;
	}

	public void setTriptime(String triptime) {
		this.triptime = triptime;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}
	public String getVerification() {
		return verification;
	}

	@Column(length = 32)
	private String budget;
	
	@Column(length = 32)
	private String size;
	
	@Column(length = 64)
	private String route;
	
	@Column(length = 128)
	private String recommendation;
	
	@Column(length = 32)
	private String insurance;
	
	@Column(length = 32)
	private String verification;

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getid() {
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

	public String getTripway() {
		return tripway;
	}

	public void setTripway(String tripway) {
		this.tripway = tripway;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getStunum() {
		return stunum;
	}

	public void setStunum(String stunum) {
		this.stunum = stunum;
	}
}