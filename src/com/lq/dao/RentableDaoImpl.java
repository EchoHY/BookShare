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
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(rentable);
	}
	@Override
	public boolean delRentable(String index) {
		String hql = "delete Rentable u where u.index = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, index);
		return (query.executeUpdate() > 0);
	}
	@Override
	public List<Rentable> getRentables() {
		String hql = "from Rentable";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
}
