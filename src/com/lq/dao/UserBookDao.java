package com.lq.dao;
import java.util.List;

import com.lq.entity.BookOwner;
import com.lq.entity.Rentable;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
import com.lq.entity.TradeLog;
public interface UserBookDao {

	public void addBookOwner(BookOwner bookowner);
	public boolean resumeBookOwner(String lastuserid,int bookid);
	public boolean updateBookOwner(String userid, int bookid, int logid);
	public BookOwner getBookOwner(int bookid);
	boolean delBookOwner(int bookid);
	public List<Sale> getAllSale(String userid);
	public List<Rented> getAllRented(String userid);
	public List<Rentable> getAllOutDate(String userid);
	public List<Rentable> getAllRentable(String userid);
	public TradeLog getLogByid(int logid);
	List<Integer> getBooksfromBookOwner(String userid);
}
