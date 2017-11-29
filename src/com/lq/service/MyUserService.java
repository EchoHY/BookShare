package com.lq.service;

import java.util.List;
import com.lq.entity.MyUser;

public interface MyUserService {

	public MyUser getMyUser(String Eaddress);
	public List<MyUser> getAllMyUser();
	public void addMyUser(MyUser myUser);
	public boolean delMyUser(String Eaddress);
	public boolean updateMyUser(MyUser myUser);
}
