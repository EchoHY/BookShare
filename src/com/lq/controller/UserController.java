package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.TradeLog;
import com.lq.entity.User;
import com.lq.service.RentableService;
import com.lq.service.RentedService;
import com.lq.service.SaleService;
import com.lq.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController{
		@Autowired
		private RentableService rentableService;
		@Autowired
		private RentedService rentedService;
		@Autowired
		private UserService userService;
		@Autowired
		private SaleService saleService;	
		/*
		 * author lmr
		 * time   2017/12/19
		 * function 显示当前用户的电话
		 * */
		@RequestMapping("/getUserPhone")
		public void getUserPhone(String userid,HttpServletRequest request,HttpServletResponse response)throws UnsupportedEncodingException{
				
			String data = "{\"phone\":\""+""+"\",\"fail\":\""+1+"\"}";
			if(userService.getOneUserInfo(userid) != null){
				data = "{\"phone\":\""+userService.getOneUserInfo(userid).getPhone()+"\",\"fail\":\""+0+"\"}";
			}
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author lmr
		 * time   2017/12/19
		 * function 显示用户的信息
		 * */
		@RequestMapping("/getUserInfo")
		public void getUserInfo(String userid,HttpServletRequest request,HttpServletResponse response)throws UnsupportedEncodingException{
			
			String data = "{\"result\":\"\",\"state\":\""+0+"\"}";
			if(userService.getOneUserInfo(userid) != null){
				data = "{\"result\":\""+JSON.toJSONString(userService.getOneUserInfo(userid))+"\",\"state\":\""+1+"\"}";
			}else{
				User user = new User();
				user.setId(userid);
				userService.addUser(user);	
			}
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author lmr
		 * time   2017/12/21
		 * function  完善用户信息
		 * */
		@RequestMapping("/complementInfo")
		public void complementInfo(int bookid,String userid,String phone,String grade,String sex,HttpServletRequest request,HttpServletResponse response){	
			/*
			 * 检查是否有这个用户，若有补全用户信息
			 * 转账+补全完个人信息后，提供给买家/租赁者 书持有者的联系方式
			 * */
			String data = "{\"result\":fail}";	
			if(userService.getOneUserInfo(userid)!=null){
				userService.updateUserInfo(userid, phone, grade, sex);
			}else {
				userService.addUser(new User(userid));
			}
			String bookowner = userService.getBookOwner(bookid).getUserid();
			String bookownertel = userService.getOneUserInfo(bookowner).getPhone();
			data = "{\"result\":"+ bookownertel +"}";
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/22
		 * function   显示正在用户手上的书 另一种意义上说我们的仓管员存储的书
		 * */
		@RequestMapping("/viewBookinhand")
	    public void userbook(String userid,HttpServletRequest request,HttpServletResponse response) {
			
			String[] userbook = {"","","",""};
			//我买进的图书，终止关系
			userbook[0] = JSON.toJSONString(userService.getAllSale(userid));
			//我正在租用的图书，租用关系，还剩天数-->提醒续借
			userbook[1] = JSON.toJSONString(userService.getAllRented(userid));
			//租期已经到且还在手上的图书，义务保留关系，保留书籍的义务，享有免费使用书籍的权利
			userbook[2] = JSON.toJSONString(userService.getAllOutDate(userid));
			//我上传还未被租用和购买的书，义务保留关系+所有者持有关系
			userbook[3] = JSON.toJSONString(userService.getAllRentable(userid));
			response.setContentType("application/json");
			String data = "{\"result\":"+ userbook +"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}		
		/*
		 * author 	lmr
		 * time		2017/12/22
		 * function 用户撤回上传的仍在自己手上的图书，删除持有者关系记录，删除书表记录
		 * */
		@RequestMapping("/cancelregister")
	    public void cacelregister(int bookid,HttpServletRequest request,HttpServletResponse response) {
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
		}
		/*
		 * author 	lmr
		 * time		2017/12/23
		 * function 租用者续借书籍
		 * */
		@RequestMapping("/reRent")
	    public String continueRent(String userid,int bookid,int period,HttpServletRequest request,HttpServletResponse response) {
			/*调用支付接口，生成交易日志log，添加former，更新bookowner的日志序号，跳转更新图片
			 * 先调用支付接口，再调用续借业务
			 * */
			int sureornot = 1;
			int now_way = 1;
			long begin_time = System.currentTimeMillis();
			long end_time = System.currentTimeMillis()+period*24*60*60*1000;
			Rentable rentable = rentableService.getOneRentable(bookid);
			BigDecimal monney = rentable.getRent_price().multiply(new BigDecimal(period));
			rentableService.moveRentable(bookid);
			rentedService.updateRented(bookid,begin_time,end_time,sureornot);
			TradeLog tradeLog  = new TradeLog(begin_time,period,now_way,userid,userid,monney); 
			userService.addlogandformer(tradeLog,userid,bookid);
			userService.updateBookOwner(userid,bookid,tradeLog.getId());
			String data = "{\"result\":\"rerentsuccess\"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
			return "/user/updatepic";
		}
		/*
		 * author 	lmr
		 * time		2017/12/23
		 * function 租用者租用结束后更新图片
		 * */
		@RequestMapping("/updatepic")
	    public void updatepic(int bookid,HttpServletRequest request,HttpServletResponse response) {
			
			String onlycode = request.getParameter("onlycode");
			String filetype   = ".jpg";//判断上传图片格式需要做
			String picName = "OpenId" +onlycode + filetype;
			String ip         = "localhost:8082/";
			String path       = "http://"+ip+"image/";//就有一个问题图片的路径怎么对应上URL
			String picture    = path+picName;
			rentableService.updateRentable(bookid, picture);
			String data = "{\"result\":\"updatepicsuccess\"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/23
		 * function 查看图书分享日志
		 * */
		@RequestMapping("/viewsharelog")
	    public void sharelog(String userid,HttpServletRequest request,HttpServletResponse response) {
			/*
			 * 卖出去的书-终止关系即书的生命终结
			 * 出租出去的书-停止分享/恢复分享
			 * */
		    List<Integer> books = userService.getBooksfromFormer(userid);
		    Map<Integer, List<TradeLog>> logmap = new HashMap<Integer,List<TradeLog>>();
		    Map<Integer, Integer> bookstatus = new HashMap<>();
		    Map<Integer, Isbn> bookinfo = new HashMap<>();
		    //HashSet<E>
		    List<TradeLog> logs = null;
		    for(Integer bookid : books){
		    	//按照降序排列
		    	logs = userService.getlogsByuserandbookid(userid, bookid);
		    	Isbn isbn = rentableService.getBookInfo(bookid);
		    	TradeLog latestlog = logs.get(logs.size()-1);
		    	int way = latestlog.getWay();
		    	long nowtime = latestlog.getDeal_time()+latestlog.getPeriod()*24*60*60*1000;
		    	if(way == 2){
		    		/*已经售出，它已经售出了
		    		 * */
		    		bookstatus.put(bookid, 0);
		    	}else if(way == 0){
					/*处于停止分享状态
					 * */
		    		bookstatus.put(bookid, 1);
				}else if(way ==1 && nowtime>System.currentTimeMillis() ){
					/*正在被租用状态
					 * */
		    		bookstatus.put(bookid, 2);
				}
				else{
					/*处于空闲状态
					 * */
					bookstatus.put(bookid, 3);
				}
		    	logmap.put(bookid, logs);
		    	bookinfo.put(bookid, isbn);
		    }
			response.setContentType("application/json");
			String data = "{\"logmap\":"+ JSON.toJSONString(logmap) +",\"bookstatus\":"+JSON.toJSONString(bookstatus)+",\"bookinfo\":"+JSON.toJSONString(bookinfo)+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/23
		 * function 停止分享，原主人取回自己出租在外的书，要求不能再租用区间
		 * */
		@RequestMapping("/stopshare")
	    public void stopshare(int bookid,String userid,HttpServletRequest request,HttpServletResponse response) {
			/*在出租表中说明书正处于被租借状态，此时原主人不可要回，返回状态码0
			 * status 为状态码
			 * */
			int status = 0;
			String data = "{\"status\":\""+status+"\",\"result\":\"此书正在被租借不可取回\"}";
			if(saleService.getOneSale(bookid) !=null){
				data = "{\"status\":\""+status+"\",\"result\":\"此书已经售出不可取回\"}";
			}
			else if(rentedService.getOneRented(bookid)==null){
				/* 不在出租表中说明处于空闲期间
				 * 返回书现持有者的手机号码
				 * */
				status = 1;
				String nowUser = userService.getBookOwner(bookid).getUserid();
				String phone = userService.getOneUserInfo(nowUser).getPhone();
				data = "{\"status\":\""+status+"\",\"result\":"+phone+"}";
				/*way =0 ,代表书不可租不可售,此时书在列表中处于不可租售状态
				 * */
				rentableService.getOneRentable(bookid);
			}
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/22
		 * function 停止分享，原主人已经取回书，此时删除书表记录，删除书持有者记录
		 * */
		@RequestMapping("/stopconfirm")
	    public void stopconfirm(int bookid,String userid,HttpServletRequest request,HttpServletResponse response) {
			
			rentableService.delRentable(bookid);
			/*now_way=0 代表原主人已取回，此书已经停止分享，需要恢复分享才能延续生命
			 *period=1025  代表原主人已取回，持续周期无限
			 *money=0 代表原主人已取回，本次交易金额为0
			 * */
			int now_way  = 0;
			int period = 1025;
			BigDecimal money = new BigDecimal(0).setScale(1, BigDecimal.ROUND_HALF_UP);
			TradeLog tradeLog  = new TradeLog(System.currentTimeMillis(),period,now_way,userService.getBookOwner(bookid).getUserid(),userid,money); 
			userService.addlogandformer(tradeLog, userid, bookid);
			userService.delBookOwner(bookid);
			String data = "{\"result\":\"success\"}";
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/
		 * function 恢复分享——注册书表———找回日志
		 * */
		@RequestMapping("/resumeshare")
	    public void resumeshare(String userid,int bookid,HttpServletRequest request,HttpServletResponse response) {
			
			boolean rentbtn = Boolean.parseBoolean(request.getParameter("rentbtn"));
			boolean sellbtn = Boolean.parseBoolean(request.getParameter("sellbtn"));
			String  onlycode= request.getParameter("onlycode");
			String filetype   = ".jpg";//判断上传图片格式需要做
			String picName = "OpenId" + "fir" + onlycode + filetype;//onlycode 作为图片名字一部分
			String ip         = "localhost:8082/";
			String path       = "http://"+ip+"image/";//就有一个问题图片的路径怎么对应上URL
			String picPath    = path+picName;
			String information= request.getParameter("isbn");
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
			/*开始注册图书，书序号手动设置
			 * */
			Rentable rentable = new Rentable();
			rentable.setId(bookid);
			rentable.setPicture(picPath);
			rentable.setInformation(information);
			rentable.setOrigin_openid(userid);
			rentable.setRent_price(new BigDecimal(rent_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setSale_price(new BigDecimal(sale_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setStart_time(start_time);
			rentable.setWay(way);
			rentableService.addRentable(rentable);
			/*注册图书完成
			 * */
			String data = "{\"result\":\"success\"}";
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/20 18:58
		 * function 尚未确认的交易信息显示界面
		 * 用户买/租用图书， sure or not = 0
		 * */
		@RequestMapping("/notconfirm")
	    public void dealnotconfirm(String userid,HttpServletRequest request,HttpServletResponse response) {
			
			response.setContentType("application/json");
			/*获得了用户持有书的序号,下一步去Sale or Rented 表中找sureornot = 0
			 * 提供书的信息包括书名和书的图片
			 * */
			List<Integer> books = userService.getBooksfromBookOwner(userid);
			String renteds = JSON.toJSONString(rentedService.getRentedwithoutConfirm(books));
			String sales = JSON.toJSONString(saleService.getSalewithoutConfirm(books));
			String data = "{\"rented\":"+ renteds +",\"sales\":"+sales+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		
}	