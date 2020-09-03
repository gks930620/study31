package com.study.free.dao;

import java.util.List;

import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public interface IFreeBoardDao {
	
	/**
	 * <b>목록건수 리턴</b> 
	 * @param searchVO 
	 * @return int;
	 */
	public int getBoardCount(FreeBoardSearchVO searchVO);
	
	/**
	 * <b>자유게시판 목록 반환</b> 
	 * @param searchVO 
	 * @return List &lt;FreeBoardVO&gt;
	 */
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) ;
	
	/**
	 * <b>글번호에 해당하는 게시물 반환</b>
	 * @param boNo
	 * @return FreeBoardVO
	 */
  public FreeBoardVO getBoard(int boNo);
  
  /**
   * <b>게시물 등록</b>
 * @param board
   * @return  영향받은 행수 
   */
  public int insertBoard(FreeBoardVO board);
  
  /**
   * <b>해당 게시물 변경</b> 
 * @param board
   * @return  영향받은 행수 
   */
  public int updateBoard(FreeBoardVO board);
  
  /**
   * <b>해당 게시물의 삭제여부를 "Y" 로 변경 </b> 
 * @param board
   * @return 영향받은 행수 
   */
  public int deleteBoard(FreeBoardVO board);
  
  /**
   * <b>해당 게시물의 조회수 1 증가</b> 
 * @param boNo
   * @return 영향받은 행수
   */
  public int increaseHit(int boNo);
  
}
