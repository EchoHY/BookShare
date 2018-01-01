package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
//import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.other.PartRentable;
import com.lq.service.IsbnService;
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
		@Autowired
		private IsbnService isbnService;
		@RequestMapping("/ofindex")
	    public void bookput(int startlocation,int size,HttpServletRequest request,HttpServletResponse response) {
			
			System.out.println(startlocation+" "+size);
	    	List<PartRentable> rentables =rentableService.getPartRentables(startlocation, size);
	    	String rentablelist = JSON.toJSONString(rentables);
	    	Long len = rentableService.getRentableLen();
	    	response.setContentType("application/json");
			String data = "{\"result\":"+ rentablelist +",\"len\":"+len+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		@RequestMapping("/ofdetail")
	    public void bookdetail(int bookid,HttpServletRequest request,HttpServletResponse response) {
	    	response.setContentType("application/json");
	    	Rentable rentable = rentableService.getOneRentable(bookid);
	    	Isbn isbn =rentableService.getBookInfo(rentable.getInformation());
			String data = "{\"rentable\":"+ JSON.toJSONString(rentable) +",\"detail\":"+ JSON.toJSONString(isbn)+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*根据关键字/书名/作者/出版社		搜索
		 * */
		@RequestMapping("/ofsearch")
	    public void ofsearch(String keyword,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
	    	System.out.println(keyword);	    	
	    	//String keyword1="";
	    	//String keyword2="";
	    	//String[] keywords = keyword.split(" ");
			List<PartRentable> rentables = isbnService.getSearchInfo(keyword);
			//rentables = isbnService.getSearchInfoByTwokey(keyword1,keyword2);
			System.out.println(rentables.size());
	    	response.setContentType("application/json");
			String data = "{\"result\":"+ JSON.toJSONString(rentables) +"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		@RequestMapping("/ofcore")
	    public void ofcore(String keyword,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
	    	System.out.println(keyword);	    	
	    	//String keyword1="";
	    	//String keyword2="";
	    	//String[] keywords = keyword.split(" ");
			List<PartRentable> rentables = isbnService.getSearchInCore(keyword);
			//rentables = isbnService.getSearchInfoByTwokey(keyword1,keyword2);
			System.out.println(rentables.size());
	    	response.setContentType("application/json");
			String data = "{\"result\":"+ JSON.toJSONString(rentables) +"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
}	