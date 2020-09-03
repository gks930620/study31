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

public class FreeModifyController implements IController {
	
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		<jsp:useBean id="board" class="com.study.free.vo.FreeBoardVO" />
//		<jsp:setProperty property="*" name="board" />
		
		FreeBoardVO board = new FreeBoardVO();
		BeanUtils.populate(board, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();
		
		try {
			freeBoardService.modifyBoard(board);
			
			// 글 수정 성공시 메시지를 보여줄 필요 없이 바로 목록으로 이동
//			return "redirect:/free/freeList.wow";
			
			// 해당 글번호의 view화면으로 가기
			return "redirect:/free/freeView.wow?boNo=" + board.getBoNo();
			
			/*
			messageVO.setResult(true)
					 .setTitle("수정 성공")
					 .setMessage("게시물이 정상적으로 수정되었습니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
			 * */
			
		} catch (BizNotFoundException e) {
			e.printStackTrace();
			messageVO.setResult(false)
					 .setTitle("글 수정 실패")
					 .setMessage("올바르게 접근해주세요.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
			
		} catch (BizPasswordNotMatchedException e) {
			e.printStackTrace();
			messageVO.setResult(false)
					 .setTitle("글 수정 실패")
					 .setMessage("비밀번호가 틀립니다.")
					 .setUrl("/free/freeList.wow")
					 .setUrlTitle("목록으로");
		}
		// 속성에 messageVO로 저장
		req.setAttribute("messageVO", messageVO);
		return "common/message";
	}	
}
