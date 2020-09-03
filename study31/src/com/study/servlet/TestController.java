package com.study.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestController extends HttpServlet{
	
	//	/study3/commend.soju?c=list : 자유게시판 목록
	//	/study3/commend.soju?c=view&boNo=12 : 자유게시판 상세보기
	
	// 1. 클라이언트의 요청을 받는다.
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cc = req.getParameter("c");
		String viewPage = "";
		
		switch (cc) {
		case "list": viewPage = "/free/freeList.jsp"; break;
		case "view": viewPage = "/free/freeView.jsp"; break;
		default: viewPage = "/index.jsp";			  break;
		}
		
		// 5. 뷰 페이지로 이동
		// (이동 전담 서블릿) RequestDispatcher
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, resp);
	}
}
