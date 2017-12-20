package com.lq.service;
import com.lq.entity.Isbn;
public interface IsbnService {
	public void addIsbnInfor(Isbn isbninfo);
	public Isbn getOneIsbninfor(String isbn);
}
