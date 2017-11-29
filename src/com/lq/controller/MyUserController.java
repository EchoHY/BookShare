package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;



import com.lq.entity.MyUser;
import com.lq.service.MyUserService;
@Controller
@SessionAttributes({"userAccount"})
@RequestMapping("/myuser")
public class MyUserController {
		@Autowired
		private MyUserService myUserService;
				
		@RequestMapping("/Validate_addMyUser")//ע����֤
	    public void Validate_addMyUser(String nickName,String Eaddress,String Qnumber,String cellphone,HttpServletRequest request,ServletResponse response,ModelMap map) {
			List<MyUser> myUsers = myUserService.getAllMyUser();
			response.setContentType("application/json");
			String result = "{\"result\":\"\"}";	
			for (MyUser myUser2 : myUsers) {
				if(Eaddress.equals(myUser2.getEaddress())){
					//result1 = "{\"result1\":\"EaddressExist\",";//\"result2\":\"\",\"result3\":\"\",\"result4\":\"\"}";
					result = "{\"result\":\"EaddressExist\"}";			
				}
				else if(nickName.equals(myUser2.getNickName())){
					//result2 = "\"result2\":\"NicknameExist\",";					
					result = "{\"result\":\"NicknameExist\"}";
				}
				else if(Qnumber.equals(myUser2.getQnumber())){
					//result3 = "\"result3\":\"QnumberExist\",";					
					result = "{\"result\":\"QnumberExist\"}";					
				}
				else if(cellphone.equals(myUser2.getCellphone())){
					//result4 = "\"result4\":\"PnumberExist\"}";					
					result = "{\"result\":\"PnumberExist\"}";
				}			  
			}
			//result = result1+result2+result3+result4;
			System.out.println(result);
			try{
				PrintWriter out = response.getWriter();
				out.write(result);
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		@RequestMapping("/addMyUser")//����֤ע����
		public String addMyUser(MyUser myUser, HttpServletRequest request,ModelMap map) {	
			try {	
				System.out.println("110");
	            System.out.println(myUser.getEaddress());
				myUserService.addMyUser(myUser);
	            return "redirect:/login1.jsp";
	        }catch (Exception e) {
	        	return "/error";
	        }       
	    }
		@RequestMapping("/logining")//��¼����
		public void logining(String Eaddress,String password,ModelMap map,HttpServletResponse response){
	        String result = "{\"result\":\"passwordError\"}";
	        response.setContentType("application/json");
			//map.addAttribute("req4","");
			//map.addAttribute("req5","");
			MyUser myUser1 =myUserService.getMyUser(Eaddress);
			//System.out.println(Eaddress);
			if(myUser1==null){
				//map.addAttribute("req4","�˺Ų�����");
				result = "{\"result\":\"EaddressError\"}";
				//System.out.println("Ewrong");
				//return "/login1";	
			}
			else {
				String mypassword = myUser1.getPassword();
				if(password.equals(mypassword))
					result = "{\"result\":\"\"}";
					//return "/success";	
				//else {
					//map.addAttribute("req5","�������");
					
					//return "/login1";
				//}					
			}
			System.out.println(result);
			try{
				PrintWriter out = response.getWriter();
				out.write(result);
			}catch(IOException e){
				e.printStackTrace();				
			}
			
		}
		@RequestMapping("/logined")//�Ѽ����¼��
		public String logined(String Eaddress,ModelMap map,HttpServletRequest request){
			System.out.println(Eaddress);
			//System.out.println(password);
			request.setAttribute("userAccount", Eaddress);
			//request.getSession().setAttribute("userAccount", Eaddress);
			//相当于同时将userAccout放入session中
			map.put("userAccount", Eaddress);
			//map.addAttribute("userAccount", userAccount);
			//request.getSession().setAttribute("userAccount", userAccount);
			return "redirect:/index1.jsp";
		}
		@RequestMapping("/validate")
		public void validate(HttpServletRequest request,HttpServletResponse response){
			String result = "{\"result\":\"unlogined\"}";
			response.setContentType("application/json");

			if(request.getSession().getAttribute("userAccount")!=null){	

				result = "{\"result\":\"islogined\"}";
			}
			System.out.println(result);
			try{
				PrintWriter out = response.getWriter();
				out.write(result);
			}catch(IOException e){
				e.printStackTrace();				
			}
			
			
		}
}
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