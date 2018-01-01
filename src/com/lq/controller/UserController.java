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
import com.lq.entity.BookOwner;
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
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
		 * function 获取用户Openid，查询是否注册该用户，若无以该Openid为账号注册
		 * */
		@RequestMapping("/register")
		public void getOpenid(String userid,HttpServletRequest request,HttpServletResponse response)throws UnsupportedEncodingException{
				
			String data = "{\"status\":\"exist\"}";
			if(userService.getOneUserInfo(userid) == null){
				userService.addUser(new User(userid));
				data = "{\"status\":\"success\"}";
			}
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
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
			
			String data = "{\"result\":\"\",\"status\":\""+0+"\"}";
			if(userService.getOneUserInfo(userid) != null){
				data = "{\"result\":\""+JSON.toJSONString(userService.getOneUserInfo(userid))+"\",\"status\":\""+1+"\"}";
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
		public void complementInfo(String userid,String phone,String grade,String sex,HttpServletRequest request,HttpServletResponse response){	
			/*
			 * 检查是否有这个用户，若有补全用户信息
			 * 转账+补全完个人信息后，提供给买家/租赁者 书持有者的联系方式
			 * */
			
			String data = "{\"result\":fail}";	
			System.out.println(userid);
			System.out.println(sex);
			System.out.println(grade);
			System.out.println(phone);
			if(userService.getOneUserInfo(userid)!=null){
				userService.updateUserInfo(userid, phone, grade, sex);
			}else {
				userService.addUser(new User(userid));
			}
			/*String bookowner = userService.getBookOwner(bookid).getUserid();
			String bookownertel = userService.getOneUserInfo(bookowner).getPhone();
			data = "{\"result\":"+ bookownertel +"}";
			*/
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
			
			//String[] userbook = {"","","",""};
			//我买进的图书，终止关系
			String bought = JSON.toJSONString(userService.getAllSale(userid));
			//我正在租用的图书，租用关系，还剩天数-->提醒续借
			String renting = JSON.toJSONString(userService.getAllRented(userid));
			//租期已经到且还在手上的图书，义务保留关系，保留书籍的义务，享有免费使用书籍的权利
			String outdate = JSON.toJSONString(userService.getAllOutDate(userid));
			//我上传还未被租用和购买的书，义务保留关系+所有者持有关系
			String rentable = JSON.toJSONString(userService.getAllRentable(userid));
			response.setContentType("application/json");
			String data = "{\"bought\":"+ bought +",\"renting\":"+ renting +",\"outdate\":"+ outdate +",\"rentable\":"+ rentable +"}";	
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
		    	
		    	Isbn info = null;
		    	Rentable rentable = rentableService.getOneRentable(bookid);
		    	Rented rented = rentedService.getOneRented(bookid);
		    	Sale sale = saleService.getOneSale(bookid);
		    	if(rentable!=null){
		    		info = rentableService.getBookInfo(rentable.getInformation());
		    	}else if(rented!=null){
		    		info = rentableService.getBookInfo(rented.getInformation());
		    	}else if(sale!=null){
		    		info = rentableService.getBookInfo(sale.getInformation());
		    	}else{
		    		info = null;
		    	}
		    	logs = userService.getlogsByuserandbookid(userid, bookid);
		    	if(logs!=null&logs.size()>0){
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
		    	}else{
		    		/*这种情况不可能存在，编写此处代码纯粹为了预防后期的更改业务造成的异常，500表示异常
		    		 * */
		    		bookstatus.put(bookid, 500);
		    	}
		    	logmap.put(bookid, logs);
		    	bookinfo.put(bookid, info);
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
	    public void stopshare(int bookid,HttpServletRequest request,HttpServletResponse response) {
			/*在出租表中说明书正处于被租借状态，此时原主人不可要回，返回状态码0
			 * status 为状态码
			 * */
			int way = 0;
			int status = 0;
			String data = "{\"status\":\""+status+"\",\"result\":\"The book is by renting now\"}";
			if(saleService.getOneSale(bookid) !=null){
				data = "{\"status\":\""+status+"\",\"result\":\"The book is sold out.\"}";
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
				way = rentableService.getOneRentable(bookid).getWay();
				rentableService.updateRentableWay(bookid,-1*way);
			}
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/* author 	lmr
		 * time		2017/12/20 18:58
		 * function 尚未确认的停止分享图书信息显示界面
		 * way = -1,-2,-3
		 * */
		@RequestMapping("/viewstopshare")
	    public void dealnotconfirm(String userid,HttpServletRequest request,HttpServletResponse response) {
			
			response.setContentType("application/json");
			List<Rentable> books = rentableService.getRentableWayBelowZero(userid);
			String data = "{\"books\":"+JSON.toJSONString(books)+"}";	
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
		@RequestMapping("/cancel")
	    public void cancelstopshare(int bookid,HttpServletRequest request,HttpServletResponse response) {
			/*在出租表中说明书正处于被租借状态，此时原主人不可要回，返回状态码0
			 * status 为状态码
			 * */
			int status = 0;
			int way = rentableService.getOneRentable(bookid).getWay();
			rentableService.updateRentableWay(bookid,-1*way);
			String data = "{\"status\":\""+status+"\",\"result\":\"success\"}";
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
		@RequestMapping("/confirm")
	    public void stopconfirm(int bookid,String userid,HttpServletRequest request,HttpServletResponse response) {
			
			rentableService.moveRentabletoStop(bookid);
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
			
			boolean rentbtn   = Boolean.parseBoolean(request.getParameter("rentbtn"));
			boolean sellbtn   = Boolean.parseBoolean(request.getParameter("sellbtn"));
			String  onlycode  = request.getParameter("onlycode");
			String filetype   = ".jpg";//判断上传图片格式需要做
			String picName 	  = "OpenId" + "fir" + onlycode + filetype;//onlycode 作为图片名字一部分
			String ip         = "localhost:8082/";
			String path       = "http://"+ip+"image/";//就有一个问题图片的路径怎么对应上URL
			String picPath    = path+picName;
			String rent_price = request.getParameter("rent_price");
			String sale_price = request.getParameter("sale_price");	
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
			rentableService.moveRentableFromStop(bookid);
			rentableService.updateRentableInfo(bookid, picPath,new BigDecimal(rent_price).setScale(1, BigDecimal.ROUND_HALF_UP),new BigDecimal(sale_price).setScale(1, BigDecimal.ROUND_HALF_UP),way);
			userService.addBookOwner(new BookOwner(bookid,userid,0));
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
		
}	