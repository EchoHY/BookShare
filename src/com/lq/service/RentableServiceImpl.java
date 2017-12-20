package com.lq.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.entity.Rentable;
import com.lq.other.PartRentable;
import com.lq.dao.RentableDao;
@Service
public class RentableServiceImpl implements RentableService{

	@Autowired
	private RentableDao rentableDao;
	@Override	public void addRentable(Rentable rentable) {
		// TODO Auto-generated method stub
		rentableDao.addRentable(rentable);
	}
	@Override
	public boolean delRentable(int index) {
		// TODO Auto-generated method stub
		return rentableDao.delRentable(index);
	}
	@Override
	public boolean updateRentable(int id,String picture) {
		// TODO Auto-generated method stub
		return rentableDao.updateRentable(id,picture);
	}
	@Override
	public List<Rentable> getRentables(int startlocation, int size) {
		// TODO Auto-generated method stub
		return rentableDao.getRentables(startlocation, size);
	}
	@Override
	public List<PartRentable> getPartRentables(int startlocation, int size) {
		// TODO Auto-generated method stub
		return rentableDao.getPartRentables(startlocation, size);
	}
	@Override
	public Rentable getOneRentable(int id) {
		// TODO Auto-generated method stub
		return rentableDao.getOneRentable(id);
	}
	@Override
	public boolean moveRentable(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.moveRentable(bookid);
	}
	@Override
	public boolean moveRentabletoSale(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.moveRentabletoSale(bookid);
	}
}
