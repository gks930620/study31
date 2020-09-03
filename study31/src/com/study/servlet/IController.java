package com.study.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IController {
	/**
	 * Front Controller 에서 작업을 위임 받아 요청에 대한 처리를 한다. <br>
	 * 비즈니스객체를 사용하여 구현하고 결과객체(모델)는 request 또는 session의 속성에 저장한다.<br>
	 * 뷰에 대한 정보를 문자열로 리턴한다.
	 * 
	 * @param HttpServletRequest  req
	 * @param HttpServletResponse resp
	 * @return ViewName
	 * @throws Exception
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
