package com.lq.dao;
import java.util.List;
import com.lq.entity.Application;

public interface ApplicationDao {
	public Application getMyApp(String id);
	public List<Application> getAllApplicate();
	public List<Application> getPartApplicate();
	public void addApplication(Application app);
	public boolean delApplication(String id);
	public boolean updateApplication(String id,String verification);
}
