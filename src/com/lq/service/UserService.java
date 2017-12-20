package com.lq.service;
import java.util.List;

import com.lq.entity.BookOwner;
import com.lq.entity.User;
public interface UserService {
	public void addUser(User user);
	public boolean delUser(String userid);
	public List<User> getALLUser();
	public boolean updateUserPhone(String userid,String phone);
	public boolean updateUserInfo(String userid,String phone,String grade,String sex);
	public User getOneUserInfo(String userid);
	public void addUserBookRelation(BookOwner bookowner);
	public boolean updateUserBookRelation(int bookid);
	public boolean resumeBookOwner(int bookid);
	public boolean updateBookOwner(String userid,int bookid);
	public void addlogandformer(String userid, int bookid, long begin_time,
			int now_way, int period);
}
