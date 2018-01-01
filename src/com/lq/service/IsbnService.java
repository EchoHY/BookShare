package com.lq.service;
import java.util.List;

import com.lq.entity.Isbn;
import com.lq.other.PartRentable;
public interface IsbnService {
	public void addIsbnInfor(Isbn isbninfo);
	public Isbn getOneIsbninfor(String isbn);
	public List<PartRentable> getSearchInfo(String keyword);
	public List<PartRentable> getSearchInCore(String keyword);
	public List<PartRentable> getSearchInfoByTwokey(String keyword,String keyword2);
}
