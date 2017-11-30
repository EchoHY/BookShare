package com.lq.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lq.entity.Rentable;
import com.lq.dao.RentableDao;
@Service
public class RentableServiceImpl implements RentableService{

	@Autowired
	private RentableDao rentableDao;
	@Override	public void addRentable(Rentable rentable) {
		// TODO Auto-generated method stub
		rentableDao.addRentable(rentable);
	}
}
