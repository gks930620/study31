package com.study.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.login.vo.UserVO;
import com.sun.net.httpserver.Filter.Chain;

public class ManagerCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		요청전에 세션에 "USER_INFO" 가 존재하면 들여보내고
//		없으면 "/login/login.wow"로 리다이렉트		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest)request).getSession();
		UserVO user = (UserVO)session.getAttribute("USER_INFO");
		if(user ==  null) {
			((HttpServletResponse)response).sendRedirect(req.getContextPath()+"/login/login.wow");
		}else if(!user.getUserRole().contains("MANAGER")) {
			resp.sendError(resp.SC_NOT_ACCEPTABLE);
		}else {
			chain.doFilter(request, response);
		}
	}
	
}

		
