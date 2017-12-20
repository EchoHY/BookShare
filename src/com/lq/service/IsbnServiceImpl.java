package com.lq.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lq.entity.Isbn;
import com.lq.dao.IsbnDao;
@Service
public class IsbnServiceImpl implements IsbnService{
	@Autowired
	private IsbnDao isbndao;
	@Override
	public void addIsbnInfor(Isbn isbninfo) {
		isbndao.addIsbn(isbninfo);
	}
	@Override
	public Isbn getOneIsbninfor(String isbn) {
		return isbndao.getOneIsbninfor(isbn);
	}
	
}
