package com.study.servlet.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JstlView extends View {
	public JstlView(StudyViewResolver viewResolver, String viewName) {
		this.viewResolver = viewResolver;
		this.viewName = viewName;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String uri = req.getRequestURI();
		String jspPath = viewResolver.getPrefix() + viewName + viewResolver.getSuffix();
		logger.debug("URI=" + uri + ", JstlViewName=" + jspPath);
		RequestDispatcher dispatcher = req.getRequestDispatcher(jspPath);
		dispatcher.forward(req, resp);
	}


}