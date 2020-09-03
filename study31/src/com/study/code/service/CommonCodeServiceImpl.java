package com.study.code.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.study.code.dao.CommonCodeDaoOracle;
import com.study.code.dao.ICommonCodeDao;
import com.study.code.vo.CodeVO;
import com.study.common.util.MybatisSqlSessionFactory;
import com.study.exception.DaoException;
import com.study.free.dao.IFreeBoardDao;

public class CommonCodeServiceImpl implements ICommonCodeService {
	private ICommonCodeDao codeDao = new CommonCodeDaoOracle();
	SqlSessionFactory factory = MybatisSqlSessionFactory.getSqlSessionFactory();

	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {
		try (SqlSession sqlSession = factory.openSession()) {
			ICommonCodeDao codeDao = sqlSession.getMapper(ICommonCodeDao.class);
			List<CodeVO> list = codeDao.getCodeListByParent(parentCode);
			return list;
		}
	}

}
