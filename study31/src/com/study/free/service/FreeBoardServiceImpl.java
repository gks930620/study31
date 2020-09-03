package com.study.free.service;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.study.common.util.MybatisSqlSessionFactory;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;

//import com.study.exception.DaoException;
//import com.study.free.dao.FreeBoardDaoOracle;

import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public class FreeBoardServiceImpl implements IFreeBoardService {

	// private IFreeBoardDao freeBoardDao = new FreeBoardDaoOracle();
	SqlSessionFactory factory = MybatisSqlSessionFactory.getSqlSessionFactory();

	@Override
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException {
		try (SqlSession sqlSession = factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			FreeBoardVO vo = freeBoardDao.getBoard(boNo);
			if (vo == null) {
				throw new BizNotFoundException("[" + boNo + "] 조회 실패");
			}
			return vo;
		}

	}

	@Override
	public void registBoard(FreeBoardVO board) {
		try (SqlSession sqlSession = factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			freeBoardDao.insertBoard(board);
			sqlSession.commit();
		}
	}

	@Override
	public void modifyBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		try (SqlSession sqlSession = factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);

			FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
			if (vo == null) {
				throw new BizNotFoundException("[" + board.getBoNo() + "] 수정 실패");
			}
			if (!(vo.getBoPass()).equals(board.getBoPass())) {
				throw new BizPasswordNotMatchedException("[" + board.getBoNo() + "] 수정 실패. 비밀번호가 틀립니다.");
			}
			int cnt = freeBoardDao.updateBoard(board);
			if (cnt < 1) {
				throw new BizNotEffectedException("[" + board.getBoNo() + "] 수정 실패");
			}
			sqlSession.commit();
		}
	}

	@Override
	public void removeBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		try (SqlSession sqlSession = factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
			if (vo == null) {
				throw new BizNotFoundException("[" + board.getBoNo() + "] 수정 실패");
			}
			if (!(vo.getBoPass()).equals(board.getBoPass())) {
				throw new BizPasswordNotMatchedException("[" + board.getBoNo() + "] 수정 실패");
			}
			int cnt = freeBoardDao.deleteBoard(board);
			if (cnt < 1) {
				throw new BizNotEffectedException("[" + board.getBoNo() + "] 수정 실패");
			}
			sqlSession.commit();
		}
	}

	@Override
	public void increaseHit(int boNo) throws BizNotEffectedException {
		try (SqlSession sqlSession = factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			int cnt = freeBoardDao.increaseHit(boNo);
			if (cnt < 1) {
				throw new BizNotEffectedException("조회수 수정 실패");
			}
			sqlSession.commit();
		}
	}

	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		try (SqlSession sqlSession = factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			// 건수를 구해서 searchVO에 설정하고, searchVO에 pageSetting()을 부르고 list 호출
			int cnt = freeBoardDao.getBoardCount(searchVO);
			searchVO.setTotalRowCount(cnt);
			searchVO.pageSetting();
			List<FreeBoardVO> list = freeBoardDao.getBoardList(searchVO);
			return list;
		}
	}
	
}
