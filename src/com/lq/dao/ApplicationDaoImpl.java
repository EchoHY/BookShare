package com.lq.dao;
import java.util.List;

import com.lq.entity.Application;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationDaoImpl implements ApplicationDao {
@Resource(name="sessionFactory")
private SessionFactory sessionFactory;
	
	@Override
	public Application getMyApp(String id) {
		String hql = "from Application u where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (Application) query.uniqueResult();
	}
	@Override
	public List<Application> getPartApplicate(){
		String hql = "from Application u where u.verification=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, "审核通过");
		return query.list();
	}
	@Override
	public List<Application> getAllApplicate() {
		String hql = "from Application";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	@Override
	public void addApplication(Application app) {
		sessionFactory.getCurrentSession().save(app);
	}
	@Override
	public boolean delApplication(String id) {
		String hql = "delete Application u where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (query.executeUpdate() > 0);
	}
	@Override
	public boolean updateApplication(String id,String verification) {
		String hql = "update Application u set u.verification=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0,verification);
		query.setString(1,id);
		return (query.executeUpdate()>0);
	}
}