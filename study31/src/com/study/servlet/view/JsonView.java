package com.study.servlet.view;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.study.login.vo.UserVO;

public class JsonView extends View {
	
	public static void main(String[] args)  throws Exception{ 
		//JsonTest
		UserVO user1=new UserVO("Milkis","사랑해요 밀키스","1004","ADMIN,MANGAGER,MEMBER");
		String jsonStr="{\"userId\":\"Milkis\",\"userName\":\"사랑해요 밀키스\",\"userPass\":\"1004\",\"userRole\":\"ADMIN,MANGAGER,MEMBER\"}";
		String jsonResult=new Gson().toJson(user1);
		System.out.println(jsonResult);
		
		ObjectMapper mapper=new ObjectMapper();
		UserVO user2=mapper.readValue(jsonStr, UserVO.class);
		System.out.println(user2.toString());
	}
	
	public JsonView() {
		contentType = "application/json; charset=UTF-8";
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setContentType(contentType);
		PrintWriter out = resp.getWriter();
		String uri = req.getRequestURI();
		Object obj = req.getAttribute("model");
		if (obj != null) {
			ObjectMapper mapper = new ObjectMapper();
			String jsonResult = mapper.writeValueAsString(obj);
			logger.debug("URI=" + uri + ", JsonView is Call");
			out.print(jsonResult);
			out.flush();
		} else {
			throw new ServletException("JsonView에 model 속성이 Null입니다.");
		}
	}
}