package com.lq.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.dao.UserDao;
import com.lq.entity.BookOwner;
import com.lq.entity.User;
@Service
public class UserServiceImpl implements UserService{
	@Autowired 
	private UserDao userDao;
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		userDao.addUser(user);
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
		return userDao.updateUserPhone(userid,phone);
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
		return userDao.getOneUserInfo(userid);
		//如果找不到此用户，则返回null
	}

	@Override
	public void addUserBookRelation(BookOwner bookowner) {
		// TODO Auto-generated method stub
		userDao.addUserBookRelation(bookowner);
	}

	@Override
	public boolean updateUserBookRelation(int bookid) {
		// TODO Auto-generated method stub
		return userDao.updateUserBookRelation(bookid);
	}

	@Override
	public boolean resumeBookOwner(int bookid) {
		// TODO Auto-generated method stub
		return userDao.resumeBookOwner(bookid);
	}
	@Override
	public boolean updateBookOwner(String userid, int bookid) {
		// TODO Auto-generated method stub
		return userDao.updateBookOwner(userid, bookid);
	}

	@Override
	public void addlogandformer(String userid, int bookid, long begin_time,
			int now_way, int period) {
		// TODO Auto-generated method stub
		userDao.addlogandformer(userid,bookid,begin_time,now_way,period);
	}
}
