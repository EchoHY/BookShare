package com.lq.controller;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



import com.lq.entity.Reply;
import com.lq.entity.Application;
import com.lq.service.ReplyService;
import com.lq.service.ApplicationService;
@Controller
//@SessionAttributes({"userAccount"})
@RequestMapping("/myPass")
public class MyPassController {
	@Autowired
	private ReplyService replyService;
	private ApplicationService applicationService;
			
	@RequestMapping("/del")
    public String del(String id,String req,HttpServletRequest request,ServletResponse response,ModelMap map) {
		if(req == "huifu"){
			if(replyService.delReply(id)){
				  request.setAttribute("replyList", replyService.getPartReply());
				  request.setAttribute("appList", applicationService.getPartApplicate());
				  //System.out.println("success");
				  return "/Controller_Pass";
			}  
			return "/error";	
		}
		else{
			if(applicationService.delApplication(id)){
				  request.setAttribute("replyList", replyService.getPartReply());
				  request.setAttribute("appList", applicationService.getPartApplicate());
				  //System.out.println("success");
				  return "/Controller_Pass";
			}  
		  return "/error";
			
		}

	}
    
	@RequestMapping("/getAll")//myApp/getAll
    public String getAll(HttpServletRequest request,ServletResponse response,ModelMap map) {
	  //System.out.println("111");
	  request.setAttribute("replyList", replyService.getPartReply());
	  request.setAttribute("appList", applicationService.getPartApplicate());
	  //System.out.println("success");
	  return "/Controller_Pass";
	}
	
	
	//updateY
	//updateN
	//del
    //myApp/addApplicate
		
}
