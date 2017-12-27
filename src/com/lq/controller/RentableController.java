package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lq.entity.BookOwner;
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.User;
import com.lq.service.IsbnService;
import com.lq.service.RentableService;
import com.lq.service.UserService;
/*
 * author 	lmr
 * time		2017/11/29 19:34
 * function	增加了书籍的注册
 * */
@Controller
@SessionAttributes({"onlycode"})
@RequestMapping("/rentable")
public class RentableController{
		@Autowired
		private RentableService rentableService;
		@Autowired
		private UserService userService;
		@Autowired
		private IsbnService isbnService;
		@RequestMapping("/bookapplication")
	    public void bookapplication(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
			request.setCharacterEncoding("utf-8");
			/* 2017/12/18
			 * 随同书信息一起上传的用户ID,检查该用户是否已经注册
			 * 若已经注册过，更新该用户的手机号码
			 * 若没有注册过，注册该用户，补充该用户的手机号
			 * */
			String userid = request.getParameter("userid");
			String origin_tel = request.getParameter("origin_tel");
			if(userService.getOneUserInfo(userid)==null){
				User user = new User();
				user.setId(userid);
				user.setPhone(origin_tel);
				userService.addUser(user);
			}
			else {
				userService.updateUserPhone(userid, origin_tel);
			}
			/*2017/12/18
			 * 开始注册图书信息
			 * */
			boolean rentbtn = Boolean.parseBoolean(request.getParameter("rentbtn"));
			boolean sellbtn = Boolean.parseBoolean(request.getParameter("sellbtn"));
			String  onlycode= request.getParameter("onlycode");
			/* 图片远程地址处理	过滤图片请求,不拦截图片URL
			 * similar-> "http://api.jisuapi.com/isbn/upload/20161010/174050_28792.jpg"
			 * */
			String filetype   = ".jpg";//判断上传图片格式需要做
			String picName = "OpenId" +onlycode + filetype;//onlycode 作为图片名字一部分
			//String fileName = "OpenId"+onlycode +filetype;
			String ip         = "localhost:8082/";
			String path       = "http://"+ip+"image/";//就有一个问题图片的路径怎么对应上URL
			String picPath    = path+picName;
			String information= request.getParameter("isbn");
			System.out.println(information);
			String rent_price = request.getParameter("rent_price");
			String sale_price = request.getParameter("sale_price");	
			long   start_time = System.currentTimeMillis();
			/* 读取交易方式
			 * 只可出租 way = 1 
			 * 只可卖     way = 2
			 * 可租可卖 way = 3
			 * */
			int way = 0;
			if(sellbtn && rentbtn)	way = 3;
			else if(sellbtn)		way = 2;
			else if(rentbtn)	    way = 1;
			else 					way = 0;
			/*
			 * addRentableandOwner
			 * */
			Rentable rentable = new Rentable();
			rentable.setPicture(picPath);
			rentable.setInformation(information);
			rentable.setOrigin_openid(userid);
			rentable.setRent_price(new BigDecimal(rent_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setSale_price(new BigDecimal(sale_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setStart_time(start_time);
			rentable.setWay(way);			
			int id = rentableService.getGeneratorId();
			rentableService.updateGeneratorId(id);
			rentable.setId(id);
			rentableService.addRentable(rentable);
			/*2017/12/18
			 * 图书信息注册完成
			 * */
			/*2017/12/18
			 * 书——用户关系表，即所有者表的注册，采用复合主码
			 * 是否能取到书序号
			 * */
			userService.addBookOwner(new BookOwner(rentable.getId(),userid,0));
			/*
			 * 向数据库isbn表查询是否有存在该书的信息
			 * */
			//information = "9787302290124";
			response.setContentType("application/json");
			//应该可以通过一种方式把map转换成JSON数据
			String data = "{\"result\":\"exist\"}";	
			if(isbnService.getOneIsbninfor(information)==null){	
				data = "{\"result\":\""+information+"\"}";	
			}			  
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		@RequestMapping("/saveisbn")
	    public void saveisbn(Isbn isbninfo,HttpServletRequest request,HttpServletResponse response){
				isbnService.addIsbnInfor(isbninfo);	
				String data = "{\"result\":\"isbn register success\"}";			  
				try{
					PrintWriter out = response.getWriter();
					out.write(data);
				}catch(IOException e){
					e.printStackTrace();				
				}
		}
		/*@RequestMapping("/rentableCancel")
		public void rentableCancel(int bookid,HttpServletRequest request,HttpServletResponse response){
				String data = "{\"result\":\"book does't exist\"}";	
				if(rentableService.getOneRentable(bookid)!=null){
					userService.delBookOwner(bookid);
					rentableService.delRentable(bookid);
					data = "{\"result\":\"cacel success\"}";	
				}
				try{
					PrintWriter out = response.getWriter();
					out.write(data);
				}catch(IOException e){
					e.printStackTrace();				
				}
		}*/
}	