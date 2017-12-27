package com.lq.dao;

import java.util.List;

import com.lq.entity.Sale;

public interface SaleDao {

	boolean updateSale(int bookid, long start_time, int sureornot);
	List<Sale> getSalewithoutConfirm(List<Integer> books);
	Sale getOneSale(int bookid);

}
