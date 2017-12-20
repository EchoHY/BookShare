package com.lq.dao;
import java.util.List;

import javax.annotation.Resource;

import com.lq.entity.Rentable;
import com.lq.entity.Rented;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class RentedDaoImpl implements RentedDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean dealCancel(int index) {
		// TODO Auto-generated method stub
		
		String hql = "select new Rentable(u.id,u.picture,u.information,"
				+ "u.origin_openid,u.start_time,u.way,u.rent_price,u.sale_price) "
				+ "from Rentable u where u.id=?";
    	//需要把rentable表中所有与rented重复的字段全部复制到new Rented中
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	//有一些问题，字段不是全部匹配上的，
    	sessionFactory.getCurrentSession().save((Rentable) query.uniqueResult());
    	hql = "delete Rented u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	return (query.executeUpdate()>0);
		
	}

	@Override
	public boolean moveToWorthless(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Rented> getRented(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rented getOneRented(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dealConfirm(int bookid,int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Rented u set u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		//sureornot == 1
		query.setInteger(0, sureornot);
		query.setInteger(1, bookid);
		return (query.executeUpdate()>0);
	}

	@Override
	public boolean updateRented(int bookid,long begin_time, long end_time,int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Rented u set u.begin_time=?,u.end_time=?,u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setLong(0, begin_time);
		query.setLong(1, end_time);
		query.setInteger(2, sureornot);
		query.setInteger(3, bookid);
		return (query.executeUpdate()>0);
	}
}