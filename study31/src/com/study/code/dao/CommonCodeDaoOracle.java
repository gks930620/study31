package com.study.code.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.study.code.vo.CodeVO;
import com.study.exception.DaoException;

public class CommonCodeDaoOracle implements ICommonCodeDao {

	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		List<CodeVO> list = new ArrayList<CodeVO>();
		
		try {
			sb.append("SELECT comm_cd  		     ");
			sb.append("     , comm_nm   		 ");
			sb.append("     , comm_parent		 ");
			sb.append("     , comm_ord 			 ");
			sb.append("  FROM comm_code			 ");
			sb.append(" WHERE comm_parent = ?	 ");
			sb.append(" ORDER BY comm_ord		 ");
		
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", ""));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			pstmt.setString(1, parentCode);
			rs = pstmt.executeQuery();
			CodeVO code = null;	// = new CodeVO()  는 나중에 문제가 되는 코드
			while (rs.next()) {
				code = new CodeVO();
				code.setCommCd(rs.getString("comm_cd"));
				code.setCommNm(rs.getString("comm_nm"));
				code.setCommParent(rs.getString("comm_parent"));
				code.setCommOrd(rs.getInt("comm_ord"));
				list.add(code);
			} // while
			return list;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

}
