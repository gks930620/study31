package com.study.free.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.study.common.sql.CommonSQL;
import com.study.exception.DaoDuplicateKeyException;
import com.study.exception.DaoException;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public class FreeBoardDaoOracle implements IFreeBoardDao {
	
	@Override
	public int getBoardCount(FreeBoardSearchVO searchVO) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("SELECT count(*)								");
			sb.append("  FROM free_board                            ");
			sb.append(" WHERE bo_del_yn = 'N'		                ");
			
			// 검색 처리
			if (StringUtils.isNotBlank(searchVO.getSearchCategory())) { // searchVO.getSearchCategory().isEmpty()와 같음.
																		// getSearchCategory()가 공백이 아니라면!
				sb.append(" AND bo_category = ? ");
			}
			if (StringUtils.isNotBlank(searchVO.getSearchWord())) {		// getSearchWord()가 공백이 아니라면!
				switch (searchVO.getSearchType()) {
				case "T": sb.append(" AND bo_title LIKE '%' || ? || '%' 	");	break;
				case "W": sb.append(" AND bo_writer LIKE '%' || ? || '%' 	"); break;
				case "C": sb.append(" AND bo_content LIKE '%' || ? || '%' 	"); break;
				}
			}
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			
			// 바인드 변수 처리
			int i = 1;
			if (StringUtils.isNotBlank(searchVO.getSearchCategory())) { // searchVO.getSearchCategory().isEmpty()와 같음.
																		// getSearchCategory()가 공백이 아니라면!
				pstmt.setString(i++, searchVO.getSearchCategory());
			}
			if (StringUtils.isNotBlank(searchVO.getSearchWord())) {		// getSearchWord()가 공백이 아니라면!
				pstmt.setString(i++, searchVO.getSearchWord());			// searchType이 무조건 설정 되어있다는 가정이 필요
			}

			rs = pstmt.executeQuery();
			int cnt = 0;
			if (rs.next()) {
				cnt = rs.getInt(1);
			} // if
			return cnt;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

	@Override
	public int insertBoard(FreeBoardVO board) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT INTO free_board (                         		");  
			sb.append("	   bo_no, bo_title, bo_category, bo_writer, bo_pass	  	");  
			sb.append("   , bo_content, bo_ip, bo_hit, bo_reg_date, bo_mod_date ");  
			sb.append("   , bo_del_yn	  										");  
			sb.append("	) VALUES (                                 				");  
			sb.append("		   seq_free_board.nextval , ? , ?					");
			sb.append("		 , ? , ?  											");
			sb.append("		 , ? , ? , 0 										");
			sb.append("		 , sysdate , null , 'N') ");
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			int i = 1;
			// 구문 실행 전에 파라미터 설정
			pstmt.setString(i++ , board.getBoTitle());
			pstmt.setString(i++ , board.getBoCategory());
			pstmt.setString(i++ , board.getBoWriter());
			pstmt.setString(i++ , board.getBoPass());
			pstmt.setString(i++ , board.getBoContent());
			pstmt.setString(i++ , board.getBoIp());
			             
			System.out.println(sb.toString());
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			if(e.getErrorCode() == 1) {		// 1은 unique 에러의 코드값
				throw new DaoDuplicateKeyException("중복된 코드 발생 =[" + board.getBoNo() + "]");
			}
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

	@Override
	public int updateBoard(FreeBoardVO board) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("UPDATE free_board                       		");  
			sb.append("   SET bo_title = ?    					   	");  
			sb.append("     , bo_category = ?    					");  
			sb.append("     , bo_content = ?      					");  
			sb.append("     , bo_mod_date = sysdate      			");  
			sb.append(" WHERE bo_no = ?								"); 
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			int i = 1;
			// 구문 실행 전에 파라미터 설정
			pstmt.setString(i++ , board.getBoTitle());
			pstmt.setString(i++ , board.getBoCategory());
			pstmt.setString(i++ , board.getBoContent());
			pstmt.setInt(i++ , board.getBoNo());
			             
			System.out.println(sb.toString());
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

	@Override
	public int deleteBoard(FreeBoardVO board) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("UPDATE free_board                       		");  
			sb.append("	  SET bo_del_yn = 'Y'   					");  
			sb.append(" WHERE bo_no = ?								"); 
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			// 구문 실행 전에 파라미터 설정
			pstmt.setInt(1 , board.getBoNo());
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

	@Override
	public int increaseHit(int boNo) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("UPDATE free_board            ");  
			sb.append("   SET bo_hit = bo_hit+1		");  
			sb.append(" WHERE bo_no  = ?			"); 
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			int i = 1;
			// 구문 실행 전에 파라미터 설정
			
			pstmt.setInt(1, boNo);
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

	@Override
	public FreeBoardVO getBoard(int boNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT   																			");
			sb.append("      bo_no		, bo_title		 , bo_pass											");
			sb.append("     , (select comm_nm from comm_code where comm_cd = bo_category) as bo_category_nm ");
			sb.append("     , bo_writer, bo_content															");
			sb.append("     , bo_ip, bo_hit, TO_CHAR(bo_reg_date,'YYYY-MM-DD HH24:MI') as bo_reg_date		");
			sb.append("     , bo_del_yn	, TO_CHAR(bo_mod_date,'YYYY-MM-DD HH24:MI') as bo_mod_date			");
			sb.append("  FROM free_board                            										");
			sb.append(" where bo_no = ? 																	");
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			pstmt.setInt(1, boNo);
			rs = pstmt.executeQuery();
			FreeBoardVO board = null;	// = new MemberVO()  는 나중에 문제가 되는 코드
			if (rs.next()) {
				board = new FreeBoardVO();
				board.setBoNo(rs.getInt("bo_no"));
				board.setBoTitle(rs.getString("bo_title"));
				board.setBoPass(rs.getString("bo_pass"));
				board.setBoCategoryNm(rs.getString("bo_category_nm"));
				board.setBoWriter(rs.getString("bo_writer"));
				board.setBoContent(rs.getString("bo_content"));
				board.setBoIp(rs.getString("bo_ip"));
				board.setBoHit(rs.getInt("bo_hit"));
				board.setBoRegDate(rs.getString("bo_reg_date"));
				board.setBoModDate(rs.getString("bo_mod_date"));
				board.setBoDelYn(rs.getString("bo_del_yn"));
			} // if
			return board;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}
	
	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		List<FreeBoardVO> list = new ArrayList<FreeBoardVO>();
		
		try {
			
			sb.append(CommonSQL.PRE_PAGING_SQL);
			
			sb.append("SELECT bo_no, comm_nm, bo_title 				 		 	");
			sb.append("		, bo_writer   			 							");
			sb.append("		, TO_CHAR(bo_reg_date,'YYYY-MM-DD') AS bo_reg_date  ");
			sb.append("		, TO_CHAR(bo_mod_date,'YYYY-MM-DD') AS bo_mod_date  ");
			sb.append("		, bo_hit   	 										");
			sb.append("  FROM free_board a, comm_code b						 	");
			sb.append(" WHERE bo_category = b.comm_cd						 	");
			sb.append("   AND bo_del_yn = 'N'								 	");
			
			// 검색 처리
			if (StringUtils.isNotBlank(searchVO.getSearchCategory())) { // searchVO.getSearchCategory().isEmpty()와 같음.
																		// getSearchCategory()가 공백이 아니라면!
				sb.append(" AND bo_category = ? ");
			}
			if (StringUtils.isNotBlank(searchVO.getSearchWord())) {		// getSearchWord()가 공백이 아니라면!
				switch (searchVO.getSearchType()) {
				case "T": sb.append(" AND bo_title LIKE '%' || ? || '%' 	");	break;
				case "W": sb.append(" AND bo_writer LIKE '%' || ? || '%' 	"); break;
				case "C": sb.append(" AND bo_content LIKE '%' || ? || '%' 	"); break;
				}
			}
						
			sb.append(" ORDER BY bo_no desc										");
			
			sb.append(CommonSQL.SUF_PAGING_SQL);
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));

			pstmt = conn.prepareStatement(sb.toString());
			// bind 변수
			int i = 1;
			// 검색 값 설정
			if (StringUtils.isNotBlank(searchVO.getSearchCategory())) { // searchVO.getSearchCategory().isEmpty()와 같음.
																		// getSearchCategory()가 공백이 아니라면!
				pstmt.setString(i++, searchVO.getSearchCategory());
			}
			if (StringUtils.isNotBlank(searchVO.getSearchWord())) {		// getSearchWord()가 공백이 아니라면!
				pstmt.setString(i++, searchVO.getSearchWord());			// searchType이 무조건 설정 되어있다는 가정이 필요
			}
			
			pstmt.setInt(i++, searchVO.getLastRow());
			pstmt.setInt(i++, searchVO.getFirstRow());
			pstmt.setInt(i++, searchVO.getLastRow());
			rs = pstmt.executeQuery();
			FreeBoardVO board = null;	// = new MemberVO()  는 나중에 문제가 되는 코드
			while (rs.next()) {
				board = new FreeBoardVO();
				board.setBoNo(rs.getInt("bo_no"));
				board.setBoCategoryNm(rs.getString("comm_nm"));
				board.setBoTitle(rs.getString("bo_title"));
				board.setBoWriter(rs.getString("bo_writer"));
				board.setBoRegDate(rs.getString("bo_reg_date"));
				board.setBoModDate(rs.getString("bo_mod_date"));
				board.setBoHit(rs.getInt("bo_hit"));
				list.add(board);
			} // while
			return list;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}	// getMemberList
	
}	// class
