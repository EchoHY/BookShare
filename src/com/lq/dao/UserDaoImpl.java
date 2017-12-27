package com.lq.dao;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.lq.entity.User;
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
		String hql = "update User u set u.phone=?,u.grade=?,u.sex=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, phone);
		query.setString(1, grade);
		query.setString(2, sex);
		query.setString(3, userid);
		return (query.executeUpdate()>0);
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
}
