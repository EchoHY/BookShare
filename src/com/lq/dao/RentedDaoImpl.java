package com.lq.dao;
import java.util.List;
import javax.annotation.Resource;
import com.lq.entity.Rented;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class RentedDaoImpl implements RentedDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

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
		String hql = "FROM Rented u Where u.id=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		return (Rented) query.uniqueResult();
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
	@SuppressWarnings("unchecked")
	@Override
	public List<Rented> getRentedwithoutConfirm(List<Integer> books) {
		// TODO Auto-generated method stub
		String hql = "FROM Rented u WHERE u.sureornot = 0 and u.id in (:alist)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("alist", books);
		return query.list();
	}
}