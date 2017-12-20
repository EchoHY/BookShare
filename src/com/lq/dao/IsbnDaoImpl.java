package com.lq.dao;
import javax.annotation.Resource;
import com.lq.entity.Isbn;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class IsbnDaoImpl implements IsbnDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void addIsbn(Isbn isbninfo) {
		sessionFactory.getCurrentSession().save(isbninfo);
	}
	@Override
	public Isbn getOneIsbninfor(String isbn) {
		String hql = "FROM Isbn u Where u.isbn=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, isbn);
		return (Isbn) query.uniqueResult();
	}
	
}