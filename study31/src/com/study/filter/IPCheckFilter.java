package com.study.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class IPCheckFilter implements Filter {

	private Map<String, String> denyIPMap;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 초기화
		denyIPMap = new HashMap();
		// 거부 아이피 등록
		denyIPMap.put("192.168.20.27", "Critical");
		denyIPMap.put("192.168.20.4", "Nomal");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (denyIPMap.containsKey(request.getRemoteAddr())) {
			request.setAttribute("IPChecked","거부IP");
			chain.doFilter(request, response);
		} else {
			request.setAttribute("IPChecked","승인IP");
			chain.doFilter(request, response);
		}
		
		

	}

}
