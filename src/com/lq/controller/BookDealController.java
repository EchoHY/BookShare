package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.lq.entity.BookOwner;
import com.lq.entity.Rentable;
import com.lq.entity.TradeLog;
import com.lq.service.RentableService;
import com.lq.service.RentedService;
import com.lq.service.SaleService;
import com.lq.service.UserService;
@Controller
@RequestMapping("/bookdeal")
public class BookDealController{
		private static final int Rent = 1;
		@Autowired
		private RentableService rentableService;
		@Autowired
		private RentedService rentedService;
		@Autowired
		private SaleService saleService;
		@Autowired
		private UserService userService;
		/*
		 * author lmr
		 * time   2017/12/19
		 * function 用户买书或者租书时，书在表间的移动
		 * 书的移动从可租可借调到已租或已借表 
		 * 书当前持有者关系变更
		 * * 若是租书period在设定的区间内，建议1-180天
		 * 若是买书period恒为0，表示永久
		 * */
		
		/*测试表移动回去的时候，主键是否还是原来的id
		 * 实验结果是否，会取用自动生成的id
		 * 
		 * 
		 * */
		@RequestMapping("/test")
	    public void testtrade(HttpServletRequest request,ServletResponse response) {
			Rentable rentable = new Rentable();
			int id = rentableService.getGeneratorId();
			rentableService.updateGeneratorId(id);
			rentable.setId(id);
			rentableService.addRentable(rentable);
		}
		@RequestMapping("/trade")
	    public void maketrade(int bookid,String userid,boolean rorbtn,int period,String phone,String grade,String sex,HttpServletRequest request,ServletResponse response) {
			
			int sureornot = 0;
			int now_way = 0;
			long begin_time = System.currentTimeMillis();
			long end_time = System.currentTimeMillis()+period*24*60*60*1000;
			Rentable rentable = rentableService.getOneRentable(bookid);
			BigDecimal monney = null;
			String data = "{\"result\":dealfail,doesn't exist this rentable }";	
			if(rentable!=null){
				if(rorbtn){
					now_way = 1;
					rentableService.moveRentable(bookid);
					rentedService.updateRented(bookid,begin_time,end_time,sureornot);
					monney = rentable.getRent_price().multiply(new BigDecimal(period));
				}
				else {
					now_way = 2;
					period = 1024;
					rentableService.moveRentabletoSale(bookid);
					saleService.updateSale(bookid,begin_time,sureornot);
					monney = rentable.getSale_price();
				}
				/*退回去的书没有主人
				 * */
				BookOwner bookOwner = userService.getBookOwner(bookid);
				if(bookOwner!=null){
					TradeLog tradeLog  = new TradeLog(begin_time,period,now_way,bookOwner.getUserid(),userid,monney); 
					userService.addlogandformer(tradeLog,rentable.getOrigin_openid(),bookid);
					userService.updateBookOwner(userid,bookid,tradeLog.getId());
					data = "{\"result\":dealsuccess }";	
				}
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
		 * time		2017/12/20 18:58
		 * function confirm交易确认发生
		 * */
		@RequestMapping("/confirm")
	    public void dealconfirm(int bookid,HttpServletRequest request,ServletResponse response) {
			
			response.setContentType("application/json");
			int sureornot = 1;
			rentedService.dealConfirm(bookid,sureornot);
	    	String data = "{\"result\":\"confirm\"}";	
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
			
			String tablename = " Sale";
			TradeLog log = userService.getLogByid(userService.getBookOwner(bookid).getLogid());
			String data = "{\"result\":\"cacelfail logs doesn't exist\"}";	
			if(log !=null){
				userService.movetoFaillog(log.getId());
				userService.resumeBookOwner(bookid,log.getManA());
				if(log.getWay() == Rent)
					tablename  = " Rented";
				rentableService.backRentable(bookid,tablename);
				//rentableService.updateRentableId(bookid);
				data = "{\"result\":\"cacel success\",\"log\":"+JSON.toJSONString(log)+"}";	
			}		
			
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
}	