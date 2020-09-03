package com.study.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingFilter implements Filter {
	// WAS Java8로 컴파일 되어있으면 doFilter 만 (Tomcat 9)
	// Java8 미만이면 init, destroy, doFilter 모두 구현해야 함
	// 소요시간, uri, ip, user-agent 
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	
		// 전처리
		long startTime = System.currentTimeMillis();
		chain.doFilter(request, response);
		
		// 후처리
		String uri = ((HttpServletRequest)request).getRequestURI();
		String ip = request.getRemoteAddr();
		logger.debug("소요시간: " + (System.currentTimeMillis() - startTime) + ", IP: " + ip + ", 요청 URI: " + uri);
		
	}

}
