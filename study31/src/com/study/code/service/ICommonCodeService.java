package com.study.code.service;

import java.util.List;

import com.study.code.vo.CodeVO;
import com.study.exception.BizException;
import com.study.exception.DaoException;

public interface ICommonCodeService {
	
	/**
	 * <b>공통코드에 조회목록을 리턴한다.</b><br>
	 * 사용법 List&lt;CodeVO&gt; list = codeDao.getCodeListByParent("JB00"); <br>
	 * <p style="color:blue;"> parentCode는 상위 코드값 입니다. ("JB00", "HB00" 등) </p>
	 * @param parentCode
	 * @return 코드목록 List<CodeVO>
	 */
	List<CodeVO> getCodeListByParent(String parentCode) ;
	
}
