package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lq.entity.Rentable;
import com.lq.service.RentableService;
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
		@RequestMapping("/bookapplication")
	    public void bookput(HttpServletRequest request,ServletResponse response,ModelMap map) throws UnsupportedEncodingException{
			request.setCharacterEncoding("utf-8");
			//测试rentbtn 和 sellbtn的传值问题未解决
			//System.out.println(rentbtn+" "+sellbtn);
			String name = request.getParameter("name");
			boolean rentbtn = Boolean.parseBoolean(request.getParameter("renbtn"));
			boolean sellbtn = Boolean.parseBoolean(request.getParameter("selltn"));
			String onlycode = request.getParameter("onlycode");
			String filetype = ".jpg";//判断上传图片格式需要做
			String picFirName = "OpenId" + "fir" + onlycode + filetype;//onlycode 作为图片名字一部分
			String picSecName = "OpenId" + "sec" + onlycode + filetype;//客户端时间+随机数 ：组合码
			String path = "http://xxx.com/"+"image/";//就有一个问题图片的路径怎么对应上URL
			String picPath = path+picFirName;
			String picPathSec = path+picSecName;
			//过滤器 -> 过滤图片请求,不拦截图片URL
			// similar-> "http://api.jisuapi.com/isbn/upload/20161010/174050_28792.jpg"
			String information= request.getParameter("information");
			String rent_price = request.getParameter("rent_price");
			String sale_price = request.getParameter("sale_price");
			String former_tel = request.getParameter("former_tel");
			String now_tel ="";
			String start_time = "";
			String way = "noneable";
			if(sellbtn && rentbtn)	way = "sell_borrowable";
			else if(sellbtn)	way = "sellable";
			else if(rentbtn)	way = "borrowable";
			else 	way = "noneable";
			String rent_history = "";
			BigDecimal income = new BigDecimal("0").setScale(1,BigDecimal.ROUND_HALF_UP);
			Rentable rentable = new Rentable();
			rentable.setName(name);
			rentable.setPicture(picPath);
			rentable.setPicturesec(picPathSec);
			rentable.setInformation(information);
			rentable.setRent_price(new BigDecimal(rent_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setSale_price(new BigDecimal(sale_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setFormer_tel(former_tel);
			rentable.setNow_tel(now_tel);
			rentable.setStart_time(start_time);
			rentable.setWay(way);
			rentable.setRent_history(rent_history);
			rentable.setIncome(income);
			//书表记录的注册完成
			rentableService.addRentable(rentable);
			//书——用户关系表，即所有者表的注册，复合主码
			response.setContentType("application/json");
			//应该可以通过一种方式把map转换成JSON数据
			String data = "{\"result\":\"\"}";	
			data = "{\"result\":\""+name+"\"}";				  
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		
}	
//String formdata = request.getParameter("data");  
//Map<String, Object> map1 = JSONObject.fromObject(name);
//将json字符串转成json对象后遍历键值对  
//name = (String)map1.get("name");
//System.out.println(name);
//JSONObject jsonObject = JSONObject.fromObject(getRequestPayload(request));
//String name=jsonObject.get("name").toString();
/*
 * Rentable rentable = new Rentable();
 * String index = "phoneNum"+FileUploadController.getCurrentDate()+FileUploadController.getCurrentTime().replaceAll(":", "：");
String name = request.getParameter("name");
String information = request.getParameter("information");
String rent_price = request.getParameter("rent_price");
String sale_price = request.getParameter("sale_price");
String former_tel = request.getParameter("former_tel");
String now_tel ="";
String start_time = "";
String way;
if(sellable == true && borrowable == true )	way = "sell_borrowable";
else if(sellable == true && borrowable == false )	way = "sellable";
else if(sellable == false && borrowable == true )	way = "borrowable";
else 	way = "noneable";

String rent_history = "";
BigDecimal income = new BigDecimal("0").setScale(1,BigDecimal.ROUND_HALF_UP);
Rentable rentable = new Rentable();
rentable.setIndex(index);
rentable.setName(name);
rentable.setInformation(information);
rentable.setRent_price(new BigDecimal(rent_price).setScale(1, BigDecimal.ROUND_HALF_UP));
rentable.setSale_price(new BigDecimal(sale_price).setScale(1, BigDecimal.ROUND_HALF_UP));
rentable.setFormer_tel(former_tel);
rentable.setNow_tel(now_tel);
rentable.setStart_time(start_time);
rentable.setWay(way);
rentable.setRent_history(rent_history);
rentable.setIncome(income);*/
//List<MyUser> myUsers = myUserService.getAllMyUser();
/*response.setContentType("application/json");
String data = "{\"result\":\"\"}";	
for (MyUser myUser2 : myUsers) {
	data = "{\"result\":\"EaddressExist\"}";				  
}
try{
	PrintWriter out = response.getWriter();
	out.write(data);
}catch(IOException e){
	e.printStackTrace();				
}*/	
			//JSONObject name=JSONObject.fromObject(request.getParameter("name"));
			//JSONObject information=JSONObject.fromObject(request.getParameter("information"));
			//JSONObject sellable=JSONObject.fromObject(request.getParameter("sellable"));
			//JSONObject rent_price=JSONObject.fromObject(request.getParameter("rent_price"));
			//JSONObject borrowable=JSONObject.fromObject(request.getParameter("borrowable"));
			//JSONObject sale_price=JSONObject.fromObject(request.getParameter("sale_price"));

			/*String ds = request.getParameter("ds");
			JSONArray json1=JSONArray.fromObject(ds);
			JSONObject jsonOne;
			Map<String,String> map1=null;
			for(int i=0;i<json1.size();i++){
			    map1 = new HashMap<String,String>();
			    jsonOne = json1.getJSONObject(i); 
			    map1.put("name", (String) jsonOne.get("name"));
			    map1.put("age", (String) jsonOne.get("age"));
			}*/

//String result1 = "{\"result1\":\"\",";//\"result2\":\"\",\"result3\":\"\",\"result4\":\"\"}";
//String result2 = "\"result2\":\"\",";
//String result3 = "\"result3\":\"\",";
//String result4 = "\"result4\":\"\"}";
//mv.setViewName("/login1");
//mv.setViewName("/login1.jsp");
//mv.addObject("a", "aaa");
//mv.addObject("req1", "1");
//mv.addObject("req2", "2");
//mv.addObject("req3", "3");
/*List<MyUser> myUsers = myUserService.getAllMyUser();
for (MyUser myUser2 : myUsers) {
	if(myUser.getEaddress().equals(myUser2.getEaddress())){
		mv.addObject("req1", "�ף������˺��ѱ�ʹ��");
		return mv;
	}
	if(myUser.getNickName().equals(myUser2.getNickName())){
		mv.addObject("req2","�ף��ǳ��ѱ�ע��");
		return mv;
	}
	if(myUser.getQnumber().equals(myUser2.getQnumber())){
		mv.addObject("req3","�ף�QQ�˺��ѱ�ʹ��");
		return mv;
	}
}
	    public String addMyUser(MyUser myUser, HttpServletRequest request,ModelMap map) {
			List<MyUser> myUsers = myUserService.getAllMyUser();
			map.addAttribute("req1", "");
			map.addAttribute("req2", "");
			map.addAttribute("req3", "");
			for (MyUser myUser2 : myUsers) {
				if(myUser.getEaddress().equals(myUser2.getEaddress())){
					map.addAttribute("req1", "�ף������˺��ѱ�ʹ��");
					return "index1";
				}
				else if(myUser.getNickName().equals(myUser2.getNickName())){
					map.addAttribute("req2", "�ף��ǳ��ѱ�ʹ��");
					return "index1";
				}
				else if(myUser.getQnumber().equals(myUser2.getQnumber())){
					map.addAttribute("req3", "�ף�QQ�˺��ѱ�ʹ��");
					return "index1";
				}
			}			
			try {	 
	            myUserService.addMyUser(myUser);
	            return "/login1";
	        }catch (Exception e) {
	        	return "/error";
	        }       
	    }
*/
//ModelAndView mv = new ModelAndView();