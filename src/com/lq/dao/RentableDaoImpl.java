package com.lq.dao;
import java.util.List;
import javax.annotation.Resource;
import com.lq.entity.Rentable;
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
}
/*
 * @Override
public MyUser getMyUser(String Eaddress){
	String hql = "from MyUser u where u.Eaddress=?";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	query.setString(0, Eaddress);
	return (MyUser) query.uniqueResult();
}
 * */