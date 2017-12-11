package com.lq.service;
import java.util.List;

import com.lq.entity.Rentable;;

public interface RentableService {
	public void addRentable(Rentable rentable);
	public boolean delRentable(int index);
	public List<Rentable> getRentables(int startlocation, int size);
	public boolean updateRentable(int id,String picture);
}
