package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.DaoDuplicateKeyException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeRegistController implements IController {
	
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		<jsp:useBean id="freeBoard" class="com.study.free.vo.FreeBoardVO" />
//		<jsp:setProperty property="*" name="freeBoard" />
		
		FreeBoardVO board = new FreeBoardVO();
		BeanUtils.populate(board, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();
		
//		board.setBoTitle(req.getParameter("boTitle"));
		
		try {
			board.setBoIp(req.getRemoteAddr());
			freeBoardService.registBoard(board);
			// 글 입력 성공시 메시지를 보여줄 필요 없이 바로 목록으로 가고자 한다면
			return "redirect:/free/freeList.wow";
			
		/*
			messageVO.setResult(true)
					 .setTitle("글 등록 성공")
					 .setMessage("작성하신 내용이 저장되었습니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
		 */

		} catch (DaoDuplicateKeyException e) {
			e.printStackTrace();
			messageVO.setResult(false)
					 .setTitle("글 등록 실패")
					 .setMessage("해당 글번호가 이미 존재합니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
		}
		// 속성에 messageVO로 저장
		req.setAttribute("messageVO", messageVO);
		return "common/message";
	}

}
