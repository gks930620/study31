package com.study.free.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeListAjaxController implements IController {
	
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		FreeBoardSearchVO searchVO = new FreeBoardSearchVO();
		BeanUtils.populate(searchVO, req.getParameterMap());
		
		req.setAttribute("searchVO", searchVO);
		
		List<FreeBoardVO> boards = freeBoardService.getBoardList(searchVO);
		req.setAttribute("board", boards);	// 결과를 속성에 저장하는 부분
		
	 	List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
	 	req.setAttribute("cateList", cateList);
	 	
	 	Map<String,Object> modelMap=new HashMap();
	 	modelMap.put("search",searchVO);
	 	modelMap.put("boardList",boards);
	 	modelMap.put("cateList",cateList);
	 	
	 	req.setAttribute("model",modelMap);

		return "jsonView";
	}

}
