package com.lq.dao;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class SaleDaoImpl implements SaleDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean updateSale(int bookid, long start_time, int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Sale u set u.start_time=?,u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setLong(0, start_time);
		query.setInteger(1, sureornot);
		query.setInteger(2, bookid);
		return (query.executeUpdate()>0);
	}
}