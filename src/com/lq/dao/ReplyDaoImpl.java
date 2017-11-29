package com.lq.dao;
import java.util.List;

import com.lq.entity.Application;
import com.lq.entity.Reply;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDaoImpl implements ReplyDao {
@Resource(name="sessionFactory")
private SessionFactory sessionFactory;
	
	@Override
	public Reply getMyReply(String id) {
		String hql = "from Reply u where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (Reply) query.uniqueResult();
	}	
	@Override
	public List<Reply> getPartReply(){
		String hql = "from Reply u where u.verification=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, "审核通过");
		return query.list();
	}
	@Override
	public List<Reply> getAllReply() {
		String hql = "from Reply";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	@Override
	public void addReply(Reply reply) {
		sessionFactory.getCurrentSession().save(reply);
	}
	@Override
	public boolean delReply(String id) {
		String hql = "delete Reply u where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		return (query.executeUpdate() > 0);
	}
	@Override
	public boolean updateReply(String id,String verification) {
		String hql = "update Reply u set u.verification=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0,verification);
		query.setString(1,id);
		return (query.executeUpdate()>0);
	}
}