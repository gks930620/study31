package com.study.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class View {
	protected final Log logger = LogFactory.getLog(getClass());
	protected String contentType = "text/html; charset=UTF-8";
	protected StudyViewResolver viewResolver;
	protected String viewName;

	public abstract void render(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}