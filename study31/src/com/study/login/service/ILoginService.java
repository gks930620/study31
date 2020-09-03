package com.study.login.service;

import com.study.exception.BizException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.vo.UserVO;

public interface ILoginService {

	/**
	 * <b>사용자 로그인 체크</b>
	 * @param  user
	 * @return UserVO
	 * @throws BizNotFoundException
	 * @throws BizPasswordNotMatchedException
	 */
	public UserVO loginCheck(UserVO user) throws BizNotFoundException, BizPasswordNotMatchedException;
	
	/**
	 * 로그아웃 할 때 처리 <br>
	 * 로그아웃 기록  
	 * @param user
	 * @throws BizException
	 */
	public void logout(UserVO user);
	
}
