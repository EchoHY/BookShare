package com.lq.dao;
import com.lq.entity.Isbn;
public interface IsbnDao {
	public void addIsbn(Isbn isbninfo);
	public Isbn getOneIsbninfor(String isbn);
}
