package com.study.member.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.study.common.sql.CommonSQL;
import com.study.exception.DaoDuplicateKeyException;
import com.study.exception.DaoException;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

public class MemberDaoOracle implements IMemberDao {

	@Override
	public int getMemberCount(MemberSearchVO searchVO) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("SELECT count(*)							");
			sb.append("  FROM member                            ");
			sb.append(" where (mem_delete = 'N' or mem_delete is null) ");
			
			// 검색 처리
			if (StringUtils.isNotBlank(searchVO.getSearchJob())) { // searchVO.getSearchCategory().isEmpty()와 같음.
				// getSearchCategory()가 공백이 아니라면!
				sb.append(" AND mem_job = ? ");
			}
			if (StringUtils.isNotBlank(searchVO.getSearchLike())) { // searchVO.getSearchCategory().isEmpty()와 같음.
			// getSearchCategory()가 공백이 아니라면!
				sb.append(" AND mem_like = ? ");
			}
			if (StringUtils.isNotBlank(searchVO.getSearchWord())) {		// getSearchWord()가 공백이 아니라면!
				switch (searchVO.getSearchType()) {
				case "NM": sb.append(" AND mem_name LIKE '%' || ? || '%' 	");	break;
				case "ID": sb.append(" AND mem_id LIKE '%' || ? || '%' 	"); 	break;
				case "HP": sb.append(" AND mem_hp LIKE '%' || ? || '%' 	"); 	break;
				case "ADDR": sb.append(" AND mem_add1 LIKE '%' || ? || '%' 	"); break;
				}
			}
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			
			// 바인드 변수 처리
			int i = 1;
			if (StringUtils.isNotBlank(searchVO.getSearchJob())) { // searchVO.getSearchCategory().isEmpty()와 같음.
																		// getSearchCategory()가 공백이 아니라면!
				pstmt.setString(i++, searchVO.getSearchJob());
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
	public int insertMember(MemberVO member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT INTO member (                         ");  
			sb.append("	      mem_id    , mem_pass  , mem_name      ");  
			sb.append("	    , mem_bir   , mem_zip   , mem_add1      ");  
			sb.append("	    , mem_add2  , mem_hp    , mem_mail      ");  
			sb.append("	    , mem_job   , mem_like  , mem_mileage   ");  
			sb.append("	    , mem_delete                            ");  
			sb.append("	) VALUES (                                  ");  
			sb.append("		   ? , ? , ?");
			sb.append("		 , ? , ? , ?");
			sb.append("		 , ? , ? , ?");
			sb.append("		 , ? , ? , 0");
			sb.append("	   	 ,'N')		"); 
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			int i = 1;
			// 구문 실행 전에 파라미터 설정
			pstmt.setString(i++ , member.getMemId());
			pstmt.setString(i++ , member.getMemPass());
			pstmt.setString(i++ , member.getMemName());
			pstmt.setString(i++ , member.getMemBir());
			pstmt.setString(i++ , member.getMemZip());
			pstmt.setString(i++ , member.getMemAdd1());
			pstmt.setString(i++ , member.getMemAdd2());
			pstmt.setString(i++ , member.getMemHp());
			pstmt.setString(i++ , member.getMemMail());
			pstmt.setString(i++ , member.getMemJob());
			pstmt.setString(i++ , member.getMemLike());
			             
			System.out.println(sb.toString());
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			if(e.getErrorCode() == 1) {		// 1은 unique 에러의 코드값
				throw new DaoDuplicateKeyException("중복된 코드 발생 =[" + member.getMemId() + "]");
			}
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

	@Override
	public int updateMember(MemberVO member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			
			sb.append("UPDATE member                       					 ");  
			sb.append("SET   mem_name = ?    , mem_bir = ?    , mem_zip = ?  ");  
			sb.append("    , mem_add1 = ?    , mem_add2 = ?   , mem_hp = ?   ");  
			sb.append("    , mem_mail = ?    , mem_job = ?    , mem_like = ? ");  
			sb.append("WHERE mem_id = ?										 "); 
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			int i = 1;
			// 구문 실행 전에 파라미터 설정
			pstmt.setString(i++ , member.getMemName());
			pstmt.setString(i++ , member.getMemBir());
			pstmt.setString(i++ , member.getMemZip());
			pstmt.setString(i++ , member.getMemAdd1());
			pstmt.setString(i++ , member.getMemAdd2());
			pstmt.setString(i++ , member.getMemHp());
			pstmt.setString(i++ , member.getMemMail());
			pstmt.setString(i++ , member.getMemJob());
			pstmt.setString(i++ , member.getMemLike());
			pstmt.setString(i++ , member.getMemId());
			             
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
	public int deleteMember(MemberVO member) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("UPDATE member                       		");  
			sb.append("	  SET mem_delete = 'Y'   					");  
			sb.append(" WHERE mem_id = ?								"); 
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			// 구문 실행 전에 파라미터 설정
			pstmt.setString(1 , member.getMemId());
			
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
	public MemberVO getMember(String memId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		
		try {
			sb.append("SELECT mem_id   , mem_pass  , mem_name   										");
			sb.append("     , TO_CHAR(mem_bir,'YYYY-MM-DD') AS mem_bir  								");
			sb.append("     , mem_zip   , mem_add1  													");
			sb.append("     , mem_add2 , mem_hp    , mem_mail   										");
			sb.append("     , mem_job  																	");
			sb.append("     , (select comm_nm from comm_code where comm_cd = mem_job) as mem_job_nm     ");
			sb.append("     , mem_like  																");
			sb.append("     , (select comm_nm from comm_code where comm_cd = mem_like) as mem_like_nm   ");
			sb.append("     , mem_mileage   															");
			sb.append("     , mem_delete                        										");
			sb.append("  FROM member                            										");
			sb.append(" where mem_id = ? 																");
			System.out.println(sb.toString().replaceAll("\\s{2,}", ""));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			MemberVO member = null;	// = new MemberVO()  는 나중에 문제가 되는 코드
			if (rs.next()) {
				member = new MemberVO();
				member.setMemId(rs.getString("mem_id"));
				member.setMemName(rs.getString("mem_name"));
				member.setMemPass(rs.getString("mem_pass"));
				member.setMemBir(rs.getString("mem_bir"));
				member.setMemZip(rs.getString("mem_zip"));
				member.setMemAdd1(rs.getString("mem_add1"));
				member.setMemAdd2(rs.getString("mem_add2"));
				member.setMemHp(rs.getString("mem_hp"));
				member.setMemMail(rs.getString("mem_mail"));
				member.setMemJob(rs.getString("mem_job"));
				member.setMemJobNm(rs.getString("mem_job_nm"));		// *
				member.setMemLike(rs.getString("mem_like"));
				member.setMemLikeNm(rs.getString("mem_like_nm"));	// *
				member.setMemMileage(rs.getInt("mem_mileage"));
				member.setMemDelete(rs.getString("mem_delete"));
			} // if
			return member;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
		}
	}

	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		try {
			sb.append(CommonSQL.PRE_PAGING_SQL);
			
			sb.append("SELECT mem_id   , mem_pass  , mem_name   		 ");
			sb.append("     , TO_CHAR(mem_bir,'YYYY-MM-DD') AS mem_bir   ");
			sb.append("     , mem_zip   , mem_add1  					 ");
			sb.append("     , mem_add2 , mem_hp    , mem_mail 			 ");
			sb.append("     , mem_job 									 ");
			sb.append("     , b.comm_nm as mem_job_nm  					 ");
			sb.append("     , mem_like 									 ");
			sb.append("     , c.comm_nm as mem_like_nm 					 ");
			sb.append("     , mem_mileage   							 ");
			sb.append("     , mem_delete              			         ");
			sb.append("  FROM member, comm_code b, comm_code c           ");
			sb.append("  WHERE mem_job = b.comm_cd     				     ");
			sb.append("  AND mem_like = c.comm_cd     				     ");
			sb.append("  AND (mem_delete = 'N' or mem_delete is null)	 ");
			
			// 검색 처리
			if (StringUtils.isNotBlank(searchVO.getSearchJob())) { // searchVO.getSearchCategory().isEmpty()와 같음.
																		// getSearchCategory()가 공백이 아니라면!
				sb.append(" AND mem_job = ? ");
			}
			if (StringUtils.isNotBlank(searchVO.getSearchLike())) { // searchVO.getSearchCategory().isEmpty()와 같음.
				// getSearchCategory()가 공백이 아니라면!
				sb.append(" AND mem_like = ? ");
			}
			if (StringUtils.isNotBlank(searchVO.getSearchWord())) {		// getSearchWord()가 공백이 아니라면!
				switch (searchVO.getSearchType()) {
				case "NM": sb.append(" AND mem_name LIKE '%' || ? || '%' 	");	break;
				case "ID": sb.append(" AND mem_id LIKE '%' || ? || '%' 		"); break;
				case "HP": sb.append(" AND mem_hp LIKE '%' || ? || '%' 		"); break;
				case "ADDR": sb.append(" AND mem_add1 LIKE '%' || ? || '%' 	"); break; 
				}
			}
						
			sb.append(" ORDER BY rownum desc										");
			
			sb.append(CommonSQL.SUF_PAGING_SQL);
						
			System.out.println(sb.toString().replaceAll("\\s{2,}", ""));

			pstmt = conn.prepareStatement(sb.toString());
			// bind 변수
			int i = 1;
			// 검색 값 설정
			if (StringUtils.isNotBlank(searchVO.getSearchJob())) { // searchVO.getSearchCategory().isEmpty()와 같음.
																		// getSearchCategory()가 공백이 아니라면!
				pstmt.setString(i++, searchVO.getSearchJob());
			}
			if (StringUtils.isNotBlank(searchVO.getSearchWord())) {		// getSearchWord()가 공백이 아니라면!
				pstmt.setString(i++, searchVO.getSearchWord());			// searchType이 무조건 설정 되어있다는 가정이 필요
			}
			
			pstmt.setInt(i++, searchVO.getLastRow());
			pstmt.setInt(i++, searchVO.getFirstRow());
			pstmt.setInt(i++, searchVO.getLastRow());
			
			rs = pstmt.executeQuery();
			MemberVO member = null;	// = new MemberVO()  는 나중에 문제가 되는 코드
			while (rs.next()) {
				member = new MemberVO();
				member.setMemId(rs.getString("mem_id"));
				member.setMemName(rs.getString("mem_name"));
				member.setMemPass(rs.getString("mem_pass"));
				member.setMemBir(rs.getString("mem_bir"));
				member.setMemZip(rs.getString("mem_zip"));
				member.setMemAdd1(rs.getString("mem_add1"));
				member.setMemAdd2(rs.getString("mem_add2"));
				member.setMemHp(rs.getString("mem_hp"));
				member.setMemMail(rs.getString("mem_mail"));
				member.setMemJob(rs.getString("mem_job"));
				member.setMemJobNm(rs.getString("mem_job_nm"));		// *
				member.setMemLike(rs.getString("mem_like"));
				member.setMemLikeNm(rs.getString("mem_like_nm"));	// *
				member.setMemMileage(rs.getInt("mem_mileage"));
				list.add(member);
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
