package com.lq.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lq.entity.Reply;
import com.lq.dao.ReplyDao;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyDao replyDao;
	@Override
	public Reply getReply(String id){
		return replyDao.getMyReply(id);		
	}
	@Override
	public List<Reply> getPartReply(){
		
		return replyDao.getPartReply();
	}
	@Override
	public List<Reply> getAllReply(){
		return replyDao.getAllReply();
	}
	@Override
	public void addReply(Reply reply){
		replyDao.addReply(reply);
	}
	@Override
	public boolean delReply(String id){
		return replyDao.delReply(id);
	}
	@Override
	public boolean updateReply(String id,String verification){
		return replyDao.updateReply(id,verification);
	}
}
