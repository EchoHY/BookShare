package com.lq.dao;
import java.util.List;
import com.lq.entity.Rentable;
public interface RentableDao {
	public void addRentable(Rentable rentable);
	public boolean delRentable(String index);
	public List<Rentable> getRentables();
}
