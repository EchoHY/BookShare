package com.lq.dao;
import javax.annotation.Resource;
import com.lq.entity.Rentable;
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
}
