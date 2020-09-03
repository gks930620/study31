package com.study.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.servlet.handler.UrlHandlerMapping;
import com.study.servlet.view.StudyViewResolver;
import com.study.servlet.view.View;

@SuppressWarnings("serial")
public class StudyDispatcherController extends HttpServlet {
	private UrlHandlerMapping handlerMapping;
	private StudyViewResolver viewResolver;

	@Override
	public void init() throws ServletException {
		// 서블릿의 초기화 메서드(init)에서 설정
		// contextConfigLocation 파라미터를 읽고 HandlerMapping 객체를 생성한다.
		// ViewResolver 객체 생성 및 초기화
		String contextConfigLocation = getInitParameter("contextConfigLocation");
		try {
			handlerMapping = new UrlHandlerMapping(getServletContext(), contextConfigLocation);
			viewResolver = new StudyViewResolver().setPrefix("/WEB-INF/views/").setSuffix(".jsp");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	} // init

	// 1. 클라이언트의 요청을 처리하려면 (service, doGet, doPost 등) 재정의 한다.
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청 처리 전 공통적인 기능이 필요하면 기술한다.( or Filter)
		req.setCharacterEncoding("utf-8");
		// 2. 요청을 분석한다.( 명령 파라미터 or URI )
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length());
		System.out.printf("요청 URI = %s\n", uri);
		try {
			String viewPage = null;
			IController controller = null;
			controller = handlerMapping.getHandler(uri);
			System.out.println("controller" + controller);
			if (controller != null) {
				// 3. 요청에 따른 기능 수행
				// 4. 기능 수행에 따른 결과(모델)를 속성에 저장한다.
				// 각각의 컨트롤(커맨드) 객체는 3., 4. 의 기능을 위임 받아 수행하고 뷰정보를 리턴한다.
				viewPage = controller.process(req, resp);
				// 5. 알맞은 뷰로 이동 (forward or redirect)
				View view = viewResolver.resolveViewName(viewPage);
				if (view != null) {
					view.render(req, resp);
				}
			} else {
				// controller가 널이면 요청에 대한 정보가 없는 것이므로
				System.out.printf("uri=[%s] 에 해당하는 컨트롤러가 존재하지 않습니다.", uri);
				resp.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
			}
		} catch (Exception e) {
			e.printStackTrace();
			// resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			throw new ServletException(e);
		}
	} // service
}
// class
