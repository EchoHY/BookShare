package com.lq.entity;
import java.io.Serializable;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "t_bookowner")
public class BookOwner implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 32)
	private int bookid;
	@Id
	@Column(length = 32)
	private String userid;
	@Column(length = 32)
	private String lastuser;
	public BookOwner(){}
	public String getLastuser() {
		return lastuser;
	}
	public void setLastuser(String lastuser) {
		this.lastuser = lastuser;
	}
	public BookOwner(int bookid,String userid){
		super();
		this.bookid = bookid;
		this.userid = userid;
	}
	/*@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
