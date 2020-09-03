package com.study.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectView extends View {
	public RedirectView(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String uri = req.getRequestURI();
		String stripViewName = viewName.substring("redirect:".length());
		logger.debug("URI=" + uri + ", RedirectViewName=" + stripViewName);
		if (stripViewName.startsWith("/")) {
			resp.sendRedirect(req.getContextPath() + stripViewName);
		} else {
			resp.sendRedirect(stripViewName);
		}
	}
}