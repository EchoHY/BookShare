package com.lq.entity;
import javax.persistence.*;
@Entity
@Table(name = "t_generator")
public class Generator {
	@Id
	@Column(length = 32)
	private String name;
	private int next_value;
	public Generator(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNext_value() {
		return next_value;
	}
	public void setNext_value(int next_value) {
		this.next_value = next_value;
	}
	
}