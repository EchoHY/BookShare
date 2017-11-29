package com.lq.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class FirstInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("执行到了afterCompletion");
		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("执行到了postHandle");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("执行到了preHandle");
		if(request.getParameter("admin")==null&&request.getParameter("password")==null)
		{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
			return false;
		}
		if(!request.getParameter("admin").equals("admin")||!request.getParameter("password").equals("233333"))
		{
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return false;
		}
		else {
				return true;
				}
	}

}
