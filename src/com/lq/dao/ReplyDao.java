package com.lq.dao;
import java.util.List;
import com.lq.entity.Reply;

public interface ReplyDao {
	public Reply getMyReply(String id);
	public List<Reply> getPartReply();
	public List<Reply> getAllReply();
	public void addReply(Reply reply);
	public boolean delReply(String id);
	public boolean updateReply(String id,String verification);
}
