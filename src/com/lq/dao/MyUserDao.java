package com.lq.dao;
import java.util.List;
import com.lq.entity.MyUser;;

public interface MyUserDao {
	public MyUser getMyUser(String Eaddress);
	public List<MyUser> getAllMyUser();
	public void addMyUser(MyUser myUser);
	public boolean delMyUser(String Eaddress);
	public boolean updateUser(MyUser myUser);
}
