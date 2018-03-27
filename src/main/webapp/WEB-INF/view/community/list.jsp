<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 글 리스트</title>
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#searchKeyword").keyup(function(){
			/* keyup 한 게 엔터다!!!*/
			if(event.key == "Enter"){
				//movePage('0');
			}
		});
	});	
</script>
<style>
td, th {
	border: 1px solid #333;
}
</style>
</head>
<body>
	<div id="wrapper" style="text-align: center;">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />

		<div>${pageExplorer.totalCount }건의게시물</div>
		<table style="margin-left: auto; margin-right: auto;">
			<tr>
				<!-- th는 볼드에 가운데 정렬 -->
				<th>ID</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
			<c:forEach items="${pageExplorer.list}" var="contents">
				<tr>
					<!-- 왼쪽 정렬 -->
					<td>${contents.id}</td>
					<td><a href="<c:url value="/read/${contents.id}"/>">${contents.title}</a>
						<c:if test="${not empty contents.displayFilename}">
							<img src="<c:url value = "/static/img/save.png"/>"
								alt="${contents.displayFilename}" style="width: 10px;" />
						</c:if></td>
					<!-- 닉네임(이메일) 0319 :: 데이터가 있을때 보여주고 없으면 탈퇴한 회원!!!-->
					<td><c:choose>
							<c:when test="${not empty contents.memberVO }">
						${contents.memberVO.nickname}&nbsp;[${contents.memberVO.email}]
						</c:when>
							<c:otherwise>탈퇴한 회원</c:otherwise>
						</c:choose></td>
					<td>${contents.writeDate}</td>
					<td>${contents.viewCount}</td>
				</tr>
			</c:forEach>
			<!-- 게시글이 없을 때!!! 등록된 게시글이 없습니다. -->
			<!-- c:if는 if만!! //choose when otherwise로 elseif 대신함 -->
			<!-- not empty / empty -->
			<c:if test="${empty pageExplorer.list}">
				<tr>
					<td colspan="5">등록된 게시글이 없습니다.</td>
				</tr>

			</c:if>
		</table>
		<form id="searchForm" onsubmit ="movePage('0')">
		${pageExplorer.make() }
			<!-- 0326  -->
			<div>
				<select id="searchType" name="searchType">
				<!-- eq 조건을 만족하면 'selected' 수행 아니면 '' :: 3항연산 -->
					<option value="1" ${search.searchType eq 1 ? 'selected' : ''}>글 제목</option>
					<option value="2" ${search.searchType eq 2 ? 'selected' : ''}>글 내용</option>
					<option value="3" ${search.searchType eq 3 ? 'selected' : ''}>글 제목 + 글 내용</option>
					<option value="4" ${search.searchType eq 4 ? 'selected' : ''}>작성자 닉네임</option>
					<option value="5" ${search.searchType eq 5 ? 'selected' : ''}>작성자 이메일</option>
					<option value="6" ${search.searchType eq 6 ? 'selected' : ''}>첨부파일 이름</option>
					<option value="7" ${search.searchType eq 7 ? 'selected' : ''}>첨부파일 형식</option>
				</select> 
				<input type="text" id="searchKeyword" name="searchKeyword" 
				value="${search.searchKeyword }"/>
				<a href="<c:url value="reset"/>">검색 초기화</a>
			</div>
		</form>
		<div>
		<a href="<c:url value="/admin/actionhistory"/>">STAFF</a>
			<a href="<c:url value="/write"/>">글쓰기</a> <a
				href="<c:url value="/logout"/>">로그아웃</a>
		</div>
	</div>
</body>
</html>