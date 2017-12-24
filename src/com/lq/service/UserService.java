package com.lq.service;
import java.util.List;

import com.lq.entity.BookOwner;
import com.lq.entity.Rentable;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
import com.lq.entity.TradeLog;
import com.lq.entity.User;
public interface UserService {
	public void addUser(User user);
	public boolean delUser(String userid);
	public List<User> getALLUser();
	public boolean updateUserPhone(String userid,String phone);
	public boolean updateUserInfo(String userid,String phone,String grade,String sex);
	public User getOneUserInfo(String userid);
	public void addBookOwner(BookOwner bookowner);
	public boolean resumeBookOwner(int bookid,String lastuserid);
	public boolean updateBookOwner(String userid,int bookid,int logid);
	public BookOwner getBookOwner(int bookid);
	public  void addlogandformer(TradeLog tradeLog, String origin_openid, int bookid);
	public List<Sale> getAllSale(String userid);
	public List<Rented> getAllRented(String userid);
	public List<Rentable> getAllOutDate(String userid);
	public List<Rentable> getAllRentable(String userid);
	public List<TradeLog> getlogsByuserandbookid(String userid, int bookid);
	public boolean delBookOwner(int bookid);
	public List<Integer> getBooksfromBookOwner(String userid);
	public TradeLog getLogByid(int logid);
	public boolean movetoFaillog(int logid,String reason);
	public List<Integer> getBooksfromFormer(String userid);
}
