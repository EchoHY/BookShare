package com.lq.dao;
import java.util.List;
import javax.annotation.Resource;
import com.lq.entity.MyUser;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MyUserDaoImpl implements MyUserDao {
@Resource(name="sessionFactory")
private SessionFactory sessionFactory;

@Override
public MyUser getMyUser(String Eaddress){
	String hql = "from MyUser u where u.Eaddress=?";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	query.setString(0, Eaddress);
	return (MyUser) query.uniqueResult();
}

@Override
public List<MyUser> getAllMyUser() {
	String hql = "from MyUser";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	return query.list();
}

@Override
public void addMyUser(MyUser myUser) {
	sessionFactory.getCurrentSession().save(myUser);
}

@Override
public boolean delMyUser(String Eaddress) {
	String hql = "delete MyUser u where u.Eaddress = ?";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	query.setString(0, Eaddress);
	return (query.executeUpdate() > 0);
}

@Override
public boolean updateUser(MyUser myUser) {
	String hql = "update MyUser u set u.password=?where u.Eaddress=?";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	//query.setString(0, myUser.getNickName());
	query.setString(0, myUser.getPassword());
	query.setString(1, myUser.getEaddress());
	return (query.executeUpdate()>0);
}
}
