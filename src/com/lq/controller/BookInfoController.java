package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.lq.entity.Rentable;
import com.lq.service.RentableService;
/*
 * author 	lmr
 * time		2017/12/11 0:46
 * function	向前台提供书籍信息
 * */
@Controller
@RequestMapping("/bookinfo")
public class BookInfoController{
		@Autowired
		private RentableService rentableService;
		@RequestMapping("/ofindex")
	    public void bookput(int startlocation,int size,HttpServletRequest request,ServletResponse response,ModelMap map) {
	    	
			System.out.println(startlocation+" "+size);
	    	List<Rentable> rentables =rentableService.getRentables(startlocation, size);
	    	//Map<String, Object> resultMap = new HashMap<String, Object>();
	    	//data.result[i].xxx
	    	//resultMap.put("result", rentables);
	    	String rentablelist = JSON.toJSONString(rentables);
	    	response.setContentType("application/json");
			String data = "{\"result\":"+ rentablelist +"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
			System.out.println(data);
		}
		
}	