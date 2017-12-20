package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lq.service.RentableService;
import com.lq.service.RentedService;
import com.lq.service.SaleService;
import com.lq.service.UserService;
@Controller
@RequestMapping("/bookdeal")
public class BookDealController{
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
		 * function 用户买书或者租书时，书在表间的移动
		 * */
		@RequestMapping("/trade")
	    public void maketrade(int bookid,String userid,boolean rentbtn,boolean sellbtn,int period,HttpServletRequest request,ServletResponse response) {
			/*
			 * 书的移动从可租可借调到已租或已借表 
			 * 书当前持有者关系变更
			 * */
			int sureornot = 0;
			int now_way = 0;
			long begin_time = System.currentTimeMillis();
			long end_time = System.currentTimeMillis()+period*24*60*60*1000;
			if(rentbtn){
				now_way = 1;
				rentableService.getOneRentable(bookid).setWay(now_way);
				rentableService.moveRentable(bookid);
				rentedService.updateRented(bookid,begin_time,end_time,sureornot);	

			}
			else {
				now_way = 2;
				rentableService.moveRentabletoSale(bookid);
				saleService.updateSale(bookid,begin_time,sureornot);	
			}
			userService.addlogandformer(userid,bookid,begin_time,now_way,period);
			userService.updateBookOwner(userid,bookid);
			
			String data = "{\"result\":dealsuccess }";	
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
		 * function confirm交易确认发生
		 * */
		@RequestMapping("/confirm")
	    public void dealconfirm(int bookid,String userid,HttpServletRequest request,ServletResponse response) {
			response.setContentType("application/json");
			int sureornot = 1;
			rentedService.dealConfirm(bookid,sureornot);
	    	String data = "{\"result\":confirmsuccess }";	
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
		 * function cancel 交易取消
		 * */
		@RequestMapping("/cancel")
	    public void dealcancel(int bookid,HttpServletRequest request,ServletResponse response) {
			
			userService.resumeBookOwner(bookid);
			rentedService.dealCancel(bookid);
			String data = "{\"result\":cacelsuccess }";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
}	