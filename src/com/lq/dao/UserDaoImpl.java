package com.lq.dao;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.lq.entity.BookOwner;
import com.lq.entity.Former;
import com.lq.entity.Rentable;
import com.lq.entity.User;
import com.lq.entity.TradeLog;
@Repository
public class UserDaoImpl implements UserDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public boolean delUser(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getALLUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUserPhone(String userid, String phone) {
		// TODO Auto-generated method stub
		String hql = "update User u set u.phone=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, phone);
		query.setString(1, userid);
		return (query.executeUpdate()>0);
	}

	@Override
	public boolean updateUserInfo(String userid, String phone, String grade,
			String sex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getOneUserInfo(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM User u Where u.id=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return (User) query.uniqueResult();
	}

	@Override
	public void addUserBookRelation(BookOwner bookowner) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(bookowner);
	}

	@Override
	public boolean updateUserBookRelation(int bookid) {
		// TODO Auto-generated method stub			
		return false;
	}

	@Override
	public boolean resumeBookOwner(int bookid) {
		// TODO Auto-generated method stub
		String hql = "update BookOwner u set u.userid=u.lastuser where book.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		return (query.executeUpdate()>0); 

	}
	@Override
	public boolean updateBookOwner(String userid, int bookid) {
		// TODO Auto-generated method stub
		String hql = "update BookOwner u set u.userid=?where u.bookid=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		query.setInteger(1, bookid);
		return (query.executeUpdate()>0);
	}

	@Override
	public void addlogandformer(String userid, int bookid, long deal_time,
			int now_way, int period) {
		// TODO Auto-generated method stub
		String hql = "FROM BookOwner u Where u.bookid=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		BookOwner bookOwner = (BookOwner)query.uniqueResult();
		hql = "FROM Rentable u Where u.bookid=? ";
		query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		Rentable rentable = (Rentable)query.uniqueResult();
		BigDecimal monney = null;
		if(now_way == 1)
			monney = rentable.getRent_price();
		else if(now_way == 2)
			monney = rentable.getSale_price();
		TradeLog tradeLog  = new TradeLog(deal_time,now_way,bookOwner.getUserid(),userid,monney); 
		sessionFactory.getCurrentSession().save(tradeLog);
		Former former = new Former(bookid,rentable.getOrigin_openid(),tradeLog.getId());
		sessionFactory.getCurrentSession().save(former);
	}
}
