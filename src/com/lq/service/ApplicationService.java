package com.lq.service;

import java.util.List;
import com.lq.entity.Application;;

public interface ApplicationService {

	public Application getApplication(String id);
	public List<Application> getPartApplicate();
	public List<Application> getAllApplicate();
	public void addApplication(Application app);
	public boolean delApplication(String id);
	public boolean updateApplication(String id,String verification);
}
