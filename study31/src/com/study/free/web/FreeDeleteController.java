package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeDeleteController implements IController {

	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();
	ResultMessageVO messageVO = new ResultMessageVO();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		FreeBoardVO board = new FreeBoardVO();
		BeanUtils.populate(board, req.getParameterMap());

		try {
			freeBoardService.removeBoard(board);

//			return "redirect:/free/freeList.wow";

			messageVO.setResult(true).setTitle("글 삭제 성공").setMessage("게시물이 삭제되었습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");

		} catch (BizNotFoundException e) {
			e.printStackTrace();
			messageVO.setResult(false).setTitle("글 삭제 실패").setMessage("올바르게 접근해주세요.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");

		} catch (BizPasswordNotMatchedException e) {
			e.printStackTrace();
			messageVO.setResult(false).setTitle("글 삭제 실패").setMessage("비밀번호가 틀립니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");
		}
		// 속성에 messageVO로 저장
		req.setAttribute("messageVO", messageVO);
		return "common/message";
		// return "/WEB-INF/views/common/message.jsp";
	}

}
