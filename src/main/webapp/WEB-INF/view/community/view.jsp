<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${community.title}</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<h1>${community.title}</h1>


		<h3>
			<c:choose>
				<c:when test="${not empty community.memberVO }">
			${community.memberVO.nickname}[${community.memberVO.email}]
			</c:when>
				<c:otherwise>탈퇴한회원</c:otherwise>
			</c:choose>
		</h3>
		<h4>${community.requestIP}| ${community.writeDate}</h4>
		<p>조회수 : ${community.viewCount} &nbsp&nbsp 추천수 :
			${community.recommendCount}</p>
		<p></p>
		<%-- <p>${community.displayFilename }</p> --%>
		<c:if test="${not empty community.displayFilename }">
			<p>
				<a href="<c:url value = "/get/${community.id }"/>">
					${community.displayFilename } </a>
			</p>
		</c:if>
		<p>${community.contents }</p>
		<a href="<c:url value = "/recommend/${community.id}"/>">추천하기</a> <a
			href="<c:url value = "/"/>">목록으로</a>
		<c:if
			test="${sessionScope.__USER__.account == community.memberVO.account }">
			<a href="<c:url value = "/modify/${community.id }"/>">수정하기</a>
			<a href="<c:url value ="/deletePage/${community.id }"/>">삭제하기</a>
		</c:if>
	</div>
</body>
</html>