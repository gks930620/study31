<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
	<title>memberList.jsp </title>
</head>
<body>
<%@include file="/WEB-INF/inc/top.jsp"%>
 <div class="container">	
	
	<h3>회원목록</h3>		
	<!-- START : 검색 폼  --> 
	<div class="panel panel-default collapse in" id="id_search_area">
	  <div class="panel-body">
	    <form name="frm_search" action="memberList.wow" method="post" class="form-horizontal">
	        <input type="hidden" name="curPage" value="${searchVO.curPage}">
	        <input type="hidden" name="rowSizePerPage" value="${searchVO.rowSizePerPage}">
	          <div class="form-group">
	            <label for="id_searchType" class="col-sm-2 control-label">검색</label>
	            <div class="col-sm-2">
	                <select id="id_searchType" name="searchType" class="form-control input-sm">	
                		<option value="NM" ${"NM" eq searchVO.searchType ? "selected='selected'": "" }>이름</option>
                		<option value="ID" ${"ID" eq searchVO.searchType ? "selected='selected'": "" }>아이디</option>
                		<option value="HP" ${"HP" eq searchVO.searchType ? "selected='selected'": "" }>핸드폰번호</option>
                		<option value="ADDR" ${"ADDR" eq searchVO.searchType ? "selected='selected'": "" }>주소</option>
	                </select>
	            </div>
	            <div class="col-sm-2">	
	              <input type="text" name="searchWord" class="form-control input-sm" value="${searchVO.searchWord}" placeholder="검색어" >			      
	            </div>
	            <div>
	            <label for="id_searchJob" class="col-sm-1 col-sm-offset control-label">직업</label>
	            <div class="col-sm-2">
	                <select id="id_searchJob" name="searchJob" class="form-control input-sm">
	                    <option value="">-- 전체 --</option>
	                    <c:forEach items="${jobList}" var="code">
	                        <option value="${code.commCd}" ${code.commCd eq searchVO.searchJob ? "selected='selected'": "" }>${code.commNm}</option>
	                    </c:forEach>
	                </select>
	            </div>
	            <label for="id_searchLike" class="col-sm-1 col-sm-offset control-label" >취미</label>
	            <div class="col-sm-2">
	                <select id="id_search" name="searchLike" class="form-control input-sm">
	                    <option value="">-- 전체 --</option>
	                    <c:forEach items="${likeList}" var="code">
	                        <option value="${code.commCd}" ${code.commCd eq searchVO.searchLike ? "selected='selected'": "" }>${code.commNm}</option>
	                    </c:forEach>
	                </select>
	            </div>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="col-sm-2 col-sm-offset-9 text-right" >
	              <button type="button" id="id_btn_reset" class="btn btn-sm btn-default">
	                    <i class="fa fa-sync"></i>
	                    &nbsp;&nbsp;초기화 
	                </button>
	            </div>     
	            <div class="col-sm-1 text-right" >
	                <button type="submit" class="btn btn-sm btn-primary ">
	                    <i class="fa fa-search"></i>
	                    &nbsp;&nbsp;검 색
	                </button>
	            </div>
	          </div>
	    </form>
	  </div>
	</div>
	<!-- END : 검색 폼  -->

	<!-- START : 목록건수 및 새글쓰기 버튼  --> 
	<div class="row" style="margin-bottom: 10px;">
	    <div class="col-sm-3 form-inline"  >
	        전체 ${searchVO.totalRowCount } 건 조회
	        <select id="id_rowSizePerPage" name="rowSizePerPage" class="form-control input-sm" >
	            <option value="10" >10</option>
	            <option value="20" >20</option>
	            <option value="30" >30</option>
	            <option value="50" >50</option>
	        </select>
	    </div>
	    
	    <div class="col-sm-1 col-sm-offset-6 text-right" >
	        <a id="id_seach_show" href="#id_search_area" class="btn btn-info btn-sm"  data-toggle="collapse"  aria-expanded="false" aria-controls="collapseExample"> 
	        <i id="searchTap" class="fas fa-chevron-up"></i>
	            <span>&nbsp;검색닫기</span>
	            
			</a>
	    </div>
	    <div class="col-sm-1 text-right" >
	        <a href="freeForm.wow" class="btn btn-primary btn-sm"> 
	            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
	            &nbsp;새글쓰기
			</a>
	    </div>
	    <div class="col-sm-1">
		<a href="memberForm.wow" class="btn btn-primary btn-sm pull-right">회원 등록</a>
		</div>
	</div>
	<!-- END : 목록건수 및 새글쓰기 버튼  --> 
	
	<table class="table table-striped table-bordered">
	<caption class="hidden">회원목록 조회</caption>
	<colgroup>
		<col style="width: 15%" />
		<col />
		<col style="width: 20%" />
		<col style="width: 20%" />
		<col style="width: 15%" />
		<col style="width: 15%" />
	</colgroup>
	<thead>
		<tr>
			<th>ID</th>
			<th>회원명</th>
			<th>HP</th>
			<th>생일</th>
			<th>직업</th>
			<th>마일리지</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="vo" items="${members}">
		
			<tr>
				<td>${vo.memId}</td>
				<td><a href="memberView.wow?memId=${vo.memId}">${vo.memName}</a></td>
				<td>${vo.memHp}</td>
				<td>${vo.memBir}</td>
				<td>${vo.memJobNm}</td>
				<td>${vo.memMileage}</td>
			</tr>
			
		</c:forEach>
		<tbody>
		</tbody>			
	</table>
	<!-- START : 페이지네이션  --> 
	<nav class="text-center">
		<mytag:paging pagingVO="${searchVO}" linkPage="memberList.wow" />
	</nav>
	<!-- END : 페이지네이션  --> 

	</div>
	<script type="text/javascript">
		// 변수 정의
		var f = document.forms['frm_search'];
		
		// 함수 정의
		
		
		// 각 이벤트 등록
		// 페이지 링크 클릭
		$('ul.pagination li a[data-page]').click(function(e) {
			e.preventDefault();		// 이벤트 전파 방지,  <a>의 기본 클릭이벤트를 막기위함
			// data-page에 있는 값을 f.curPage.value에 설정, 서브밋
			f.curPage.value = $(this).data('page');
			f.submit();
		});		// ul.pagination li a[data-page]
		
		// 검색 버튼 클릭
		$(f).submit(function(e) {
			e.preventDefault();	
			f.curPage.value = 1;
			f.submit();
		});	// f.submit
		
		// 목록 갯수 변경
		// id_rowSizePerPage 변경되었을 때
		// 페이지 1, 목록수 = 현재값 으로 변경 후 서브밋
		$('#id_rowSizePerPage').change(function(e) {
			f.curPage.value = 1;
			f.rowSizePerPage.value = this.value;
			f.submit();
		});		// '#id_rowSizePerPage'.change
		
		// 초기화 버튼 클릭
		$('#id_btn_reset').click(function() {
			f.curPage.value = 1;
			f.searchWord.value = "";
			f.searchType.options[0].selected = true;
			f.searchJob.options[0].selected = true;
			f.searchLike.options[0].selected = true;
		}); // #id_btn_reset.click
		
		// jQuery에서 클래스 삭제 및 추가, 내용 변경
		$('#id_search_area').on('shown.bs.collapse', function() {
			$('#searchTap').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			$('#id_seach_show span').html('&nbsp; 검색열기');
		});
	
		$('#id_search_area').on('hidden.bs.collapse', function() {
			$('#searchTap').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			$('#id_seach_show span').html('&nbsp; 검색열기');
		});
		
	</script>
</div>
</body>
</html>