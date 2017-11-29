package com.lq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.entity.MyUser;
import com.lq.dao.MyUserDao;

@Service
public class MyUserServiceImpl implements MyUserService{

	@Autowired
	private MyUserDao myUserDao;
		
	@Override
	public MyUser getMyUser(String Eaddress) {
		// TODO Auto-generated method stub
		return myUserDao.getMyUser(Eaddress);
	}

	@Override
	public List<MyUser> getAllMyUser() {
		// TODO Auto-generated method stub
		return myUserDao.getAllMyUser();
	}

	@Override
	public void addMyUser(MyUser myUser) {
		// TODO Auto-generated method stub
		myUserDao.addMyUser(myUser);
	}

	@Override
	public boolean delMyUser(String Eaddress) {
		// TODO Auto-generated method stub
		return myUserDao.delMyUser(Eaddress);
	}

	@Override
	public boolean updateMyUser(MyUser myUser) {
		// TODO Auto-generated method stub
		return myUserDao.updateUser(myUser);
	}

}
