package com.lq.dao;
import java.util.List;
import com.lq.entity.User;
public interface UserDao {
	public void addUser(User user);
	public boolean delUser(String userid);
	public List<User> getALLUser();
	public boolean updateUserPhone(String userid,String phone);
	public boolean updateUserInfo(String userid,String phone,String grade,String sex);
	public User getOneUserInfo(String userid);
}

