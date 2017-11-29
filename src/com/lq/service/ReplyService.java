package com.lq.service;

import java.util.List;
import com.lq.entity.Reply;;

public interface ReplyService {

	public Reply getReply(String id);
	public List<Reply> getPartReply();
	public List<Reply> getAllReply();
	public void addReply(Reply reply);
	public boolean delReply(String id);
	public boolean updateReply(String id,String verification);
}
