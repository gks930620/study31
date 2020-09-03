<%@ tag language="java" pageEncoding="UTF-8"%>
<%-- paging tag --%>
<%@ tag trimDirectiveWhitespaces="true" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="pagingVO" required="true" type="com.study.common.vo.PagingVO" %>
<%@ attribute name="linkPage" type="java.lang.String" %>
<%-- paging.tag
		linkPage = "freeList.wow" -> 그대로
		linkPage = "/free/freeList.wow" -> /study3/free/freeList.wow : 컨텍스트 경로 추가
		linkPage = "http://xxxx/freeList.wow" -> 그대로 
--%>
<c:if test='${fn:startsWith(linkPage,"/")}'>
	<c:set var="linkPage" value="${pageContext.request.contextPath}${linkPage}"/>
</c:if>
<ul class="pagination">
    <!-- 이전 페이지 -->
    <c:if test="${pagingVO.firstPage > 1}">
     <li><a href="${linkPage}?curPage=${pagingVO.firstPage-1}" data-page="${pagingVO.firstPage-1}" ><span aria-hidden="true">&laquo;</span></a></li>
    </c:if>

    <!-- 페이지 넘버링  -->
    <c:forEach var="i" begin="${pagingVO.firstPage}" end="${pagingVO.lastPage}">
    	<c:if test="${pagingVO.curPage == i }">
      <li class="active"><a href="#">${i}</a></li>
    	</c:if>
    	<c:if test="${pagingVO.curPage != i }">
      <li><a href="${linkPage}?curPage=${i}"  data-page="${i}" >${i}</a></li>
    	</c:if>
    </c:forEach>

    <!-- 다음  페이지  -->
    <c:if test="${pagingVO.lastPage < pagingVO.totalPageCount}">
     <li><a href="${linkPage}?curPage=${pagingVO.lastPage+1}" data-page="${pagingVO.lastPage+1}" ><span aria-hidden="true">&raquo;</span></a></li>
    </c:if>
</ul>	