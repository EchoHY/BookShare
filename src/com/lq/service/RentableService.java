package com.lq.service;
import java.util.List;

import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.other.PartRentable;
public interface RentableService {
	public void addRentable(Rentable rentable);
	public boolean moveRentable(int bookid);
	public boolean moveRentabletoSale(int bookid);
	public boolean backRentable(int bookid,String tablename);
	public boolean delRentable(int index);
	public List<Rentable> getRentables(int startlocation, int size);
	public Rentable getOneRentable(int id);
	public List<PartRentable> getPartRentables(int startlocation, int size);
	public boolean updateRentable(int id,String picture);
	public Isbn getBookInfo(int index);
	public boolean updateRentableId(int bookid);
	public int getGeneratorId();
	public boolean updateGeneratorId(int id);
}
