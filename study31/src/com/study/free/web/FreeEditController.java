package com.study.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeEditController implements IController {
	
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();
	ResultMessageVO messageVO = new ResultMessageVO();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	 	List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
	 	req.setAttribute("cateList", cateList);
	 	
	 	try {
		 	int boNo = Integer.parseInt(req.getParameter("boNo")); 
		 	FreeBoardVO board = freeBoardService.getBoard(boNo);
		 	req.setAttribute("board", board);
		 	return "free/freeEdit";

		 //	return "/WEB-INF/views/free/freeEdit.jsp";
		 } catch(BizNotFoundException ex){
			 ex.printStackTrace();
			 ResultMessageVO message = new ResultMessageVO();
			 message.setResult(false)
			 		.setTitle("조회 실패")
			 		.setMessage("해당 글이 존재하지 않습니다.")
			 		.setUrl("/free/freeList.wow")
			 		.setUrlTitle("목록으로");
			 req.setAttribute("massageVO", message);
			 return "common.massage";
			 //return "/WEB-INF/views/common.massage.jsp";
		 }
	}
}
