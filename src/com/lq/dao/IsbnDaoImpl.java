package com.lq.dao;
import java.util.List;

import javax.annotation.Resource;

import com.lq.entity.Isbn;
import com.lq.other.PartRentable;

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
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchInfo(String keyword) {
		// TODO Auto-generated method stub 
		/*,(length(title)-length("+keyword+")) as rn1,(length(author)-length("+keyword+")) as rn2,(length(subtitle)-length("+keyword+")) as rn3,(length(keyword)-length("+keyword+")) as rn4,(length(publisher)-length("+keyword+")) as rn5 
		 order by rn1,rn2,rn3,rn4,rn5
		 * */
		String hql= "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = t.isbn and u.information in( select isbn "
				+ "from Isbn where  title like '%"+keyword+"%' or subtitle like '%"+keyword+"%' or author like '%"+keyword+"%' or publisher like '%"+keyword+"%' or keyword like '%"+keyword+"%' )";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchInfoByTwokey(String keyword,String keyword2){
		// TODO Auto-generated method stub
		String hql= "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = t.isbn and u.information "
				+ "in(select isbn,length(title) as r1,length(author) as r2,length(keyword) as r3,length(publisher) as r4,length(subtitle) as r5,length("+keyword+")) as k1,length("+keyword2+")) as k2 "
				+ "from Isbn where title like '%"+keyword+"%' or subtitle like '%"+keyword+"%' or author like '%"+keyword2+"%' or publisher like '%"+keyword2+"%' or keyword like '%"+keyword2+"%' "
				+ "order by (r1-k1+r3-k2),(r2-k1+r3-k2),(r1-k1+r4-k2), (r2-k1+r4-k2),(r1-k1+r5-k2),(r2-k1+r5-k2))";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchInCore(String keyword) {
		// TODO Auto-generated method stub
		/*,(length(title)-length("+keyword+")) as rn1,(length(author)-length("+keyword+")) as rn2,(length(subtitle)-length("+keyword+")) as rn3,(length(keyword)-length("+keyword+")) as rn4,(length(publisher)-length("+keyword+")) as rn5 
		 order by rn1,rn2,rn3,rn4,rn5
		 * */
		int length = keyword.length();
		String hql= "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = t.isbn and u.information in( select isbn,(length(title) as rn1 "
				+ "from Isbn where  title like '%"+keyword+"%' or subtitle like '%"+keyword+"%' or author like '%"+keyword+"%' or publisher like '%"+keyword+"%' or keyword like '%"+keyword+"%' order by (rn1-"+length+") ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
}