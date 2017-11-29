package com.lq.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
@Controller
@RequestMapping("/upload")
public class FileUploadController implements ServletContextAware{
	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext context){
		this.servletContext = context;
	}	
	public static String getCurrentDate() {  
	    String pattern = "yyyy-MM-dd";  
	    SimpleDateFormat df = new SimpleDateFormat(pattern);  
	    Date today = new Date();  
	    String tString = df.format(today);  
	    return tString;  
	} 
	public static String getCurrentTime() {  
	    String pattern = "HH:mm:ss";  
	    SimpleDateFormat df = new SimpleDateFormat(pattern);  
	    Date today = new Date();  
	    String tString = df.format(today);  
	    return tString;  
	} 
	 private boolean saveFile(CommonsMultipartFile file,String username) { 
		    // 判断文件是否为空 
		    if (!file.isEmpty()) { 
		      try { 
		        // 文件保存路径 
		    //String filepath = this.servletContext.getRealPath("/tmp/");
		    String filepath = "/usr/image";
		    String	fileName = file.getOriginalFilename();
		    String	fileType = fileName.substring(fileName.lastIndexOf("."));
		    int num=fileType.length();//得到后缀名长度  
		       System.out.println(filepath);
		     String fileOtherName=fileName.substring(0, fileName.length()-num);//得到文件名。去掉了后缀  
		     fileName = fileOtherName+username+"_"+getCurrentDate()+"_"+getCurrentTime().replaceAll(":", "：")+fileType;
		     //fileName = fileOtherName+"_"+getCurrentDate()+fileType;
		        // 转存文件 
		        file.transferTo(new File(filepath,fileName)); 
		        return true; 
		      } catch (Exception e) { 
		        e.printStackTrace(); 
		      } 
		    } 
		    return false; 
		  }  
	@RequestMapping(value ="/uploading",method = RequestMethod.POST)
	public String uploading(@RequestParam CommonsMultipartFile[] files,HttpServletRequest request){	
		if(files!=null&&files.length>0){ 
			     //循环获取file数组中得文件 
				String username = (String)request.getSession().getAttribute("username");
			     for(int i = 0;i<files.length;i++){ 
			    	 CommonsMultipartFile file = files[i]; 
			       //保存文件 
			       saveFile(file,username); 
			     } 
			     return "redirect:/index1.jsp"; 
		}else{
				
				 return "/error";
		}		
	}	
	 
}
