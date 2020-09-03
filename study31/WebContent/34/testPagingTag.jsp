<%@page import="com.study.free.vo.FreeBoardSearchVO"%>
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
<title>testPagingTag.jsp</title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp" %>
<div class="container">
<%
	FreeBoardSearchVO searchVO = new FreeBoardSearchVO();
	searchVO.setCurPage(3);
	searchVO.setTotalRowCount(53);
	searchVO.pageSetting();
	request.setAttribute("searchVO", searchVO);
%>

<nav class="text-center">
    <ul class="pagination">
        <!-- 이전 페이지 -->
        <c:if test="${searchVO.firstPage > 1}">
	        <li><a href="freeList.wow?curPage=${searchVO.firstPage-1}" data-page="${searchVO.firstPage-1}" ><span aria-hidden="true">&laquo;</span></a></li>
        </c:if>

        <!-- 페이지 넘버링  -->
        <c:forEach var="i" begin="${searchVO.firstPage}" end="${searchVO.lastPage}">
        	<c:if test="${searchVO.curPage == i }">
		        <li class="active"><a href="#">${i}</a></li>
        	</c:if>
        	<c:if test="${searchVO.curPage != i }">
		        <li><a href="freeList.wow?curPage=${i}"  data-page="${i}" >${i}</a></li>
        	</c:if>
        </c:forEach>

        <!-- 다음  페이지  -->
        <c:if test="${searchVO.lastPage < searchVO.totalPageCount}">
	        <li><a href="freeList.wow?curPage=${searchVO.lastPage+1}" data-page="${searchVO.lastPage+1}" ><span aria-hidden="true">&raquo;</span></a></li>
        </c:if>
    </ul>	
</nav>
<hr>

<nav class="text-center">
	<mytag:paging pagingVO="${searchVO}" linkPage="freeList.wow" />
</nav>

</div> <!-- container -->
</body>
</html>