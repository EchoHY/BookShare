package com.lq.controller;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;




import com.lq.entity.Reply;
import com.lq.service.ReplyService;
@Controller
//@SessionAttributes({"userAccount"})
@RequestMapping("/myReply")
public class MyReplyController {
	@Autowired
	private ReplyService replyService;
			
	@RequestMapping("/updateY")
    public String updateY(String id,HttpServletRequest request,ServletResponse response,ModelMap map) {
		if(replyService.updateReply(id,"Pass")){
			  request.setAttribute("replyList", replyService.getAllReply());
			  //System.out.println("success");
			  return "/controllerpage/Controller_Reply";
		}  
	  return "/error";
	}
	
	@RequestMapping("/updateN")
    public String updateN(String id,HttpServletRequest request,ServletResponse response,ModelMap map) {
		if(replyService.updateReply(id,"Fail")){
			  request.setAttribute("replyList", replyService.getAllReply());
			  //System.out.println("success");
			  return "/controllerpage/Controller_Reply";
		}  
	  return "/error";
	}
	@RequestMapping("/del")
    public String del(String id,HttpServletRequest request,ServletResponse response,ModelMap map) {
		if(replyService.delReply(id)){
			  request.setAttribute("replyList", replyService.getAllReply());
			  //System.out.println("success");
			  return "/controllerpage/Controller_Reply";
		}  
	  return "/error";
	}
    
	@RequestMapping("/getAll")//myApp/getAll
    public String getAll(HttpServletRequest request,ServletResponse response,ModelMap map) {
	  request.setAttribute("replyList", replyService.getAllReply());
	  //System.out.println("success");
	  return "/controllerpage/Controller_Reply";
	}
	
	@RequestMapping("/addReply")
    public String addReply(Reply reply,String username,HttpServletRequest request) {
		try{
			replyService.addReply(reply);
			request.getSession().setAttribute("username", username);
			return "redirect:/applicationtwo.jsp";
		}catch(Exception e){
			return "/error";
		}		
	}
		
}
