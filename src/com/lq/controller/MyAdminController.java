package com.lq.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lq.service.ApplicationService;
@Controller
//@SessionAttributes({"userAccount"})
@RequestMapping("/myadmin")
public class MyAdminController {
	@Autowired
	private ApplicationService applicationService;
			
	@RequestMapping("/logining")
	public String logining(String admin,String password,ModelMap map,HttpServletRequest request,HttpServletResponse response){
       
		if(admin.equals("admin")&&password.equals("233333")){
			request.getSession().setAttribute("admin", "logined");
			return "/controllerpage/Controller";
		}
		else {
			return "/ControllerLogin";			
		}
		
	}
	@RequestMapping("/validate")
	public void validate(HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"unlogined\"}";
		response.setContentType("application/json");
		if(request.getSession().getAttribute("admin")!=null){	
			result = "{\"result\":\"islogined\"}";
		}
		try{
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch(IOException e){
			e.printStackTrace();				
		}		
	}
	@RequestMapping("/validate2")
	public void validate2(HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"unlogined\"}";
		response.setContentType("application/json");
		if(request.getSession().getAttribute("admin")!=null){	
			result = "{\"result\":\"islogined\"}";
		}
		try{
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch(IOException e){
			e.printStackTrace();				
		}
	}
	@RequestMapping("/validate3")
	public void validate3(HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"unlogined\"}";
		response.setContentType("application/json");
		if(request.getSession().getAttribute("admin")!=null){	
			result = "{\"result\":\"islogined\"}";
		}
		try{
			PrintWriter out = response.getWriter();
			out.write(result);
		}catch(IOException e){
			e.printStackTrace();				
		}		
	}	
}
