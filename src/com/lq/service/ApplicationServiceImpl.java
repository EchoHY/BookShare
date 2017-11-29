package com.lq.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lq.entity.Application;
import com.lq.dao.ApplicationDao;

@Service
public class ApplicationServiceImpl implements ApplicationService{

	@Autowired
	private ApplicationDao applicationDao;
	@Override
	public Application getApplication(String id){
		return applicationDao.getMyApp(id);		
	}
	@Override
	public List<Application> getPartApplicate(){
		return applicationDao.getPartApplicate();
	}
	@Override
	public List<Application> getAllApplicate(){
		return applicationDao.getAllApplicate();
	}
	@Override
	public void addApplication(Application app){
		applicationDao.addApplication(app);
	}
	@Override
	public boolean delApplication(String id){
		return applicationDao.delApplication(id);
	}
	@Override
	public boolean updateApplication(String id,String verification){
		return applicationDao.updateApplication(id,verification);
	}
}
