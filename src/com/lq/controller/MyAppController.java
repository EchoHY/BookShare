package com.lq.controller;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



import com.lq.entity.Application;
import com.lq.service.ApplicationService;
@Controller
//@SessionAttributes({"userAccount"})
@RequestMapping("/myApp")
public class MyAppController {
	@Autowired
	private ApplicationService applicationService;
			
	@RequestMapping("/updateY")
    public String updateY(String id,HttpServletRequest request,ServletResponse response,ModelMap map) {
		if(applicationService.updateApplication(id,"Pass")){
			  request.setAttribute("appList", applicationService.getAllApplicate());
			  //System.out.println("success");
			  return "/controllerpage/Controller_ALL";
		}  
	  return "/error";
	}
	
	@RequestMapping("/updateN")
    public String updateN(String id,HttpServletRequest request,ServletResponse response,ModelMap map) {
		if(applicationService.updateApplication(id,"Fail")){
			  request.setAttribute("appList", applicationService.getAllApplicate());
			  //System.out.println("success");
			  return "/controllerpage/Controller_ALL";
		}  
	  return "/error";
	}
	@RequestMapping("del")
    public String del(String id,HttpServletRequest request,ServletResponse response,ModelMap map) {
		if(applicationService.delApplication(id)){
			  request.setAttribute("appList", applicationService.getAllApplicate());
			  //System.out.println("success");
			  return "/controllerpage/Controller_ALL";
		}  
	  return "/error";
	}
    
	@RequestMapping("/getAll")//myApp/getAll
    public String getAll(HttpServletRequest request,ServletResponse response,ModelMap map) {
	  request.setAttribute("appList", applicationService.getAllApplicate());
	  //System.out.println("success");
	  return "/controllerpage/Controller_ALL";
	}
	
	@RequestMapping("/addApplicate")
    public String addApplicate(Application application,String username,HttpServletRequest request) {
		try{
			applicationService.addApplication(application);
			request.getSession().setAttribute("username", username);
			return "redirect:/applicationtwo.jsp";
		}catch(Exception e){
			return "/error";
		}		
	}
	
	
	//updateY
	//updateN
	//del
    //myApp/addApplicate
		
}
