package com.lq.dao;
import java.util.List;

import com.lq.entity.Rented;
public interface RentedDao {
	public boolean dealCancel(int index);
	public boolean moveToWorthless(int index);
	public List<Rented> getRented(String userid);
	public Rented getOneRented(int id);
	public boolean dealConfirm(int bookid, int sureornot);
	public boolean updateRented(int bookid, long begin_time, long end_time,int sureornot);
}