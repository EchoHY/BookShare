package com.lq.dao;
import java.util.List;
import javax.annotation.Resource;
import com.lq.entity.Rentable;
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
		String hql = "delete Rentable u where u.index = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, index);
		return (query.executeUpdate() > 0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rentable> getRentables(int startlocation, int size) {
		//String hql = "FROM Rentable order by createdTime desc";
		String hql = "FROM Rentable";
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
	@Override
	public boolean moveRentable(int index) {
		
		String hql = "select new Rented(u.id,u.picture,u.information,"
				+ "u.origin_openid,u.start_time,u.way,u.rent_price,u.sale_price) "
				+ "from Rentable u where u.id=?";
    	//需要把rentable表中所有与rented重复的字段全部复制到new Rented中
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	//有一些问题，字段不是全部匹配上的，
    	sessionFactory.getCurrentSession().save((Rented) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	return (query.executeUpdate()>0);
	}
	@Override
	public boolean moveRentabletoSale(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select new Sale(u.id,u.picture,u.information,"
				+ "u.origin_openid,u.start_time,u.way,u.rent_price,u.sale_price) "
				+ "from Rentable u where u.id=?";
    	//需要把rentable表中所有与sale重复的字段全部复制到new Sale中
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	//有一些问题，字段不是全部匹配上的，
    	sessionFactory.getCurrentSession().save((Sale) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getPartRentables(int startlocation, int size) {
		String hql = "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price) "
				+ "FROM Rentable u";
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

}