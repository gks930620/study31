package com.study.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.IController;

public class MemberRegistController implements IController {

	private IMemberService memberService = new MemberServiceImpl();
	private ICommonCodeService codeService = new CommonCodeServiceImpl();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();
		
		try {
			memberService.registMember(member);
			return "redirect:/member/memberList.wow";
			
		} catch(BizDuplicateKeyException ex) {
			ex.printStackTrace();
			messageVO.setResult(false)
					 .setTitle("글 등록 실패")
					 .setMessage("해당 아이디 이미 존재합니다.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
		}
		// 속성에 messageVO로 저장
		req.setAttribute("messageVO", messageVO);
		return "common/message";
	}
}
