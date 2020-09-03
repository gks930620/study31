package com.study.free.service;

import java.util.List;

import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public interface IFreeBoardService {
	
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO);
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException;	
	public void registBoard(FreeBoardVO board);	
	public void modifyBoard(FreeBoardVO board) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException;	
	public void removeBoard(FreeBoardVO board) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException; 	
	public void increaseHit(int boNo) throws BizNotEffectedException;
}
