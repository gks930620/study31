package com.study.servlet.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestToViewNameTranslator extends View {
	public RequestToViewNameTranslator(StudyViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
// 뷰이름이 지정되지 않은 경우 현재 요청 URI 에서 뷰이름을 생성한다.
		String uri = req.getRequestURI();

		String[] sql = uri.split("/");
		String uri2 = sql[sql.length - 2] + "/" + sql[sql.length - 1];

		if (uri2.indexOf(";") > 0) {
			uri2 = uri2.substring(0, uri2.indexOf(";"));
		}
		if (uri2.indexOf(".") > 0) {
			uri = uri2.substring(0, uri2.indexOf("."));	
		}

		String uriViewName = uri;

// 과제 : 아래의 조건을 만족하도록 변수 uriViewName 을 변경하시오.
// uriViewName에서 컨텍스트 경로, 확장자, 세미콜론이 있다면 제거
// 예 : "/study31/free/freeList.wow;JSESSIONID=MILKIS1004" -> "free/freeList"

		String jspPath = viewResolver.getPrefix() + uriViewName + viewResolver.getSuffix();
		logger.debug("URI=" + uri + ", RequestToViewNameTranslator=" + jspPath);
		RequestDispatcher dispatcher = req.getRequestDispatcher(jspPath);
		dispatcher.forward(req, resp);
	}

	public static void main(String[] args) {
		String uri = "/study31/free/freeList.wow;JSESSIONID=MILKIS1004";
		String ctp = "/study31";
		String uriViewName = uri; // free/freeList

		String[] sql = uri.split("/");
		String uri2 = sql[2] + "/" + sql[3];
		uri = uri2.substring(0, uri2.indexOf(";"));
		uri = uri.substring(0, uri2.indexOf("."));
		System.out.println(uri);
	}

}