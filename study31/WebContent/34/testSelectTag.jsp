<%@page import="com.study.code.vo.CodeVO"%>
<%@page import="com.study.code.service.CommonCodeServiceImpl"%>
<%@page import="com.study.code.service.ICommonCodeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/inc/header.jsp" %>
<title>testSelectTag.jsp</title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp" %>
<div class="container">
<%
	ICommonCodeService codeService = new CommonCodeServiceImpl();
	List<CodeVO> jobs = codeService.getCodeListByParent("JB00");
	pageContext.setAttribute("jobs", jobs);
	pageContext.setAttribute("myJob", "JB06");
%>
<select name="memJob" class="form-control input-sm" required="required" 
		id="id_memJob">
	<option value="">-- 직업 선택 --</option>
	<c:forEach items="${jobs}" var="code">
		<option value="${code.commCd}" ${code.commCd eq myJob ? "selected='selected'": "" }>${code.commNm}</option>
	</c:forEach>
</select>

<hr>
<mytag:select items="${jobs}" name="memJob" itemLabel="commNm" itemValue="commCd" value="${myJob}"
			  class="form-control input-sm" required="required" id="id_memJob2" />
<hr>
<mytag:select items="${jobs}" name="memJob" itemLabel="commNm" itemValue="commCd" value="${myJob}"
			  class="form-control input-sm" required="required" id="id_memJob2" >
	<option value="">-- 직업 선택 --</option>
</mytag:select>
</div> <!-- container -->
</body>
</html>