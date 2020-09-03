<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
	<title>memberList.jsp </title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>
 <div class="container">	
	<h3>회원등록</h3>		
	<jsp:useBean id="member" class="com.study.member.vo.MemberVO" />
	<jsp:setProperty property="*" name="member" />
	${member}
	<%
		// 1. 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 2. 커넥션 구하기
		conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "java", "oracle");
		
		// 3. 구문 객체 생성
		stmt = conn.createStatement();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO member (                         ");  
		sb.append("	      mem_id    , mem_pass  , mem_name      ");  
		sb.append("	    , mem_bir   , mem_zip   , mem_add1      ");  
		sb.append("	    , mem_add2  , mem_hp    , mem_mail      ");  
		sb.append("	    , mem_job   , mem_like  , mem_mileage   ");  
		sb.append("	    , mem_delete                            ");  
		sb.append("	) VALUES (                                  ");  
		sb.append("	    '" + member.getMemId() + "'"); 
		sb.append("	   ,'" + member.getMemPass() + "'"); 
		sb.append("	   ,'" + member.getMemName() + "'"); 
		sb.append("	   ,'" + member.getMemBir() + "'"); 
		sb.append("	   ,'" + member.getMemZip()+ "'"); 
		sb.append("	   ,'" + member.getMemAdd1()+ "'"); 
		sb.append("	   ,'" + member.getMemAdd2()+ "'"); 
		sb.append("	   ,'" + member.getMemHp()+ "'"); 
		sb.append("	   ,'" + member.getMemMail()+ "'"); 
		sb.append("	   ,'" + member.getMemJob()+ "'"); 
		sb.append("	   ,'" + member.getMemLike()+ "'"); 
		sb.append("	   ,'0'"); 
		sb.append("	   ,'N'"); 
		sb.append(")"	); 
		             
		System.out.println(sb.toString());
		int cnt = stmt.executeUpdate(sb.toString());
		if(cnt > 0){
	%>
		<div class="alert alert-success">
			정상적으로 회원 등록 되었습니다.
		</div>		
	<%
		}
		// 자원 종료
		if(rs != null) try{ rs.close();} catch(SQLException e) {}
		if(stmt != null) try{ stmt.close();} catch(SQLException e) {}
		if(conn != null) try{ conn.close();} catch(SQLException e) {}
	%>
</body>
</html>