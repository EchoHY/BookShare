package com.lq.dao;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.lq.entity.Generator;
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.Rentablestop;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
import com.lq.other.PartRentable;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class RentableDaoImpl implements RentableDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Override
	public void addRentable(Rentable rentable) {		
	
		sessionFactory.getCurrentSession().save(rentable);
	}
	@Override
	public boolean delRentable(int index) {
		String hql = "delete Rentable u where u.id= ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, index);
		return (query.executeUpdate() > 0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rentable> getRentables(int startlocation, int size) {
		String hql = "FROM Rentable order by start_time desc";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setMaxResults(size);
		query.setFirstResult(startlocation);
		return query.list();
	}
	@Override
	public boolean updateRentable(int id, String picture){
		String hql = "update Rentable u set u.picture=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, picture);
		query.setInteger(1, id);
		return (query.executeUpdate()>0);
	}
	/*
	 * 需要把rentable表中所有与rented重复的字段全部复制到new Rented中
	 */
	@Override
	public boolean moveRentable(int index) {
		// TODO Auto-generated method stub
		String hql = "select new Rented(u.id,u.picture,u.information,u.origin_openid,"
				+ "u.start_time,u.way,u.rent_price,u.sale_price) from Rentable u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	sessionFactory.getCurrentSession().save((Rented) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	return (query.executeUpdate()>0);
	}
	/*
	 * 需要把rentable表中所有与sale重复的字段全部复制到new Sale中
	 */
	@Override
	public boolean moveRentabletoSale(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select new Sale(u.id,u.picture,u.information,u.origin_openid,u.start_time,"
				+ "u.way,u.rent_price,u.sale_price) from Rentable u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	sessionFactory.getCurrentSession().save((Sale) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
	}
	@Override
	public boolean backRentable(int bookid,String tablename) {
		// TODO Auto-generated method stub	
		String hql = "select new Rentable(u.id,u.picture,u.information,u.origin_openid,u.start_time,"
				+ "u.way,u.rent_price,u.sale_price) from "+ tablename +" u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	sessionFactory.getCurrentSession().save((Rentable)query.uniqueResult());    
    	hql = "delete "+tablename+" u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
		
	}
	@Override
	public boolean updateRentableWay(int bookid,int way) {
		// TODO Auto-generated method stub
    	String hql = "update Rentable u set u.way=? where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, way);
		query.setInteger(1, bookid);
		return (query.executeUpdate()>0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getPartRentables(int startlocation, int size) {
		String hql = "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price,t.title) "
				+ "FROM Rentable u,Isbn t WHERE u.information = t.isbn and u.way>0 order by u.start_time";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setMaxResults(size);
		query.setFirstResult(startlocation);
		return query.list();
	}
	@Override
	public Rentable getOneRentable(int id) {
		String hql = "FROM Rentable u Where u.id=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		return (Rentable) query.uniqueResult();
	}
	@Override
	public Isbn getBookInfo(String isbn) {
		// TODO Auto-generated method stub
			String hql = "FROM Isbn u Where u.isbn=? ";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString(0, isbn);
			return (Isbn) query.uniqueResult();
	}
	@Override
	public int getGeneratorId() {
		
		// TODO Auto-generated method stub
		String hql = "SELECT next_value FROM Generator WHERE name =?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0,"rentable_id");	
		if(query.uniqueResult() ==null ){
			sessionFactory.getCurrentSession().save(new Generator("rentable_id",0));
			return 0;
		}
		return (Integer)query.uniqueResult();
	}
	@Override
	public boolean updateGeneratorId(int id) {
		// TODO Auto-generated method stub
		String hql = "update Generator set next_value=? WHERE name =?";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id+1);
		query.setString(1,"rentable_id");
		return(query.executeUpdate()>0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rentable> getRentableWayBelowZero(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM Rentable WHERE way<0";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	@Override
	public boolean moveRentabletoStop(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select new Rentablestop(u.id,u.information,u.origin_openid,"
				+ "u.start_time)from Rentable u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	sessionFactory.getCurrentSession().save((Rentablestop) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
	}
	@Override
	public boolean moveRentableFromStop(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select new Rentable(u.id,u.information,u.origin_openid,u.start_time) from Rentablestop u"
				+ " where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		sessionFactory.getCurrentSession().save((Rentable) query.uniqueResult());
		hql = "delete Rentablestop u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
	}
	@Override
	public boolean updateRentableInfo(int bookid, String picPath,
			BigDecimal rent_price, BigDecimal sale_price, int way) {
		// TODO Auto-generated method stub
		String hql = "update Rentable set picture=?,rent_price=?,sale_price=?,way=? WHERE id =?";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0,picPath);
		query.setBigDecimal(1, rent_price);
		query.setBigDecimal(2, sale_price);
		query.setInteger(3, way);
		query.setInteger(4,bookid);
		return(query.executeUpdate()>0);
	}
	@Override
	public Long getRentableLen() {
		// TODO Auto-generated method stub
		String hql = "SELECT COUNT(*) FROM Rentable";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (Long)query.uniqueResult();
	}

}