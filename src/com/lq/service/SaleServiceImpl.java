package com.lq.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.dao.RentedDao;
import com.lq.dao.SaleDao;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
@Service
public class SaleServiceImpl implements SaleService{

	@Autowired
	private SaleDao saleDao;

	@Override
	public boolean dealCancel(int bookid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveToWorthless(int bookid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Sale> getSale(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sale getOneSale(int bookid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dealConfirm(int bookid, int sureornot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSale(int bookid, long start_time, int sureornot) {
		// TODO Auto-generated method stub
		return saleDao.updateSale(bookid,start_time,sureornot);
	}

}
