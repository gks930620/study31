package com.study.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.IController;

public class MemberModifyController implements IController {

	private IMemberService memberService = new MemberServiceImpl();
	private ICommonCodeService codeService = new CommonCodeServiceImpl();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();
		try {
			memberService.modifyMember(member);
			return "redirect:/member/memberView.wow?memId=" + member.getMemId();
			
		} catch(BizNotEffectedException ex) {
			ex.printStackTrace();
			messageVO.setResult(false)
					 .setTitle("수정에 실패했습니다.")
					 .setMessage("아이디나 비밀번호를 확인해주세요.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
					
		} catch(BizNotFoundException ex) {
			ex.printStackTrace();
			messageVO.setResult(false)
					 .setTitle("회원이 존재하지 않습니다.")
					 .setMessage("올바르게 접근해주세요.")
					 .setUrl("/member/memberList.wow")
					 .setUrlTitle("목록으로");
		}
		// 속성에 messageVO로 저장
		req.setAttribute("messageVO", messageVO);
		return "common/message";
	}
}
