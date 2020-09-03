package com.study.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.web.FreeDeleteController;
import com.study.free.web.FreeEditController;
import com.study.free.web.FreeFormController;
import com.study.free.web.FreeListController;
import com.study.free.web.FreeModifyController;
import com.study.free.web.FreeRegistController;
import com.study.free.web.FreeViewController;

public class SimpleController extends HttpServlet{
	public static void main(String[] args) {
		String cp = "/study3";		// 컨텍스트패스
		String t = "/study3/free/freeList.wow";
//		t = t.substring(7);
		t = t.substring(cp.length());
		System.out.println(t);
	}
	
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();
	 
	
	// 1. 클라이언트의 요청을 받는다.
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// 공통적으로 필요한 부분은 컨트롤러에서 처리
		request.setCharacterEncoding("utf-8");
		// 2. 요청분석 (uri)
		String uri = request.getRequestURI();		// /study3/free/freeList.wow
		uri = uri.substring(request.getContextPath().length());		// /free/freeList.wow
		System.out.println("uri : " + uri);
		
		String viewPage = "";
		IController controller = null;
		
		
		// 3. 모델을 사용하여 처리 (if의 각 부분마다 처리)
		// 4. 결과를 속성에 저장 (if의 각 부분마다 처리)
		if("/free/freeList.wow".equals(uri)) {
			/*
			FreeBoardSearchVO searchVO = new FreeBoardSearchVO();
			List<FreeBoardVO> board = freeBoardService.getBoardList(searchVO);
			request.setAttribute("board", board);	// 결과를 속성에 저장하는 부분
			
		 	List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
		 	request.setAttribute("cateList", cateList);
		 	
		 	viewPage = "/free/freeList.jsp";
			*/
			
			// ↑내용 FreeListController로 옮겼으니
			controller = new FreeListController();
		 	
		} else if ("/free/freeView.wow".equals(uri)) {
			/*
			try {
		 		int boNo = Integer.parseInt(request.getParameter("boNo")); 
		 		
		 		FreeBoardVO free = freeBoardService.getBoard(boNo);
		 		if(free != null) {
		 			freeBoardService.increaseHit(boNo);
		 		}
			 	
			 	request.setAttribute("free", free);
			 } catch(Exception ex){
				 request.setAttribute("ex", ex);
			 }
			viewPage = "/free/freeView.jsp";
			*/
			controller = new FreeViewController();
			
		} else if ("/free/freeForm.wow".equals(uri)) {
			/*
			List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
		 	request.setAttribute("cateList", cateList);

		 	viewPage = "/free/freeForm.jsp";
			 */
			controller = new FreeFormController();
		} else if ("/free/freeRegist.wow".equals(uri)) {
			controller = new FreeRegistController();
		} else if ("/free/freeModify.wow".equals(uri)) {
			controller = new FreeModifyController();
		} else if ("/free/freeEdit.wow".equals(uri)) {
			controller = new FreeEditController();
		} else if ("/free/freeDelete.wow".equals(uri)) {
			controller = new FreeDeleteController();
		}
		
		if (controller != null) {
			try {
				viewPage = controller.process(request, resp);
				System.out.println("viewPage : " + viewPage);

				// 5. 뷰 페이지로 이동
				// (이동 전담 서블릿) RequestDispatcher
				RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
				dispatcher.forward(request, resp);
			} catch (Exception e) {
				e.printStackTrace();	// 500 
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			// 요청에 대한 컨트롤러가 존재하지 않으므로 404 던지기
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, uri + "에 해당하는 페이지가 존재하지 않습니다.");
						// HttpServletResponse.SC_NOT_FOUND 대신에 404 만 써도 됨
		}
	}
}
