<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내가 쓴 글들</title>
<link type="text/css" rel="stylesheet"	href="<c:url value="/static/css/common.css"/>"/>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
$().ready(function(){
	$("#toggle").change(function(){
		var checked = $(this).prop("checked");
		var checkbox = $("input[type=checkbox][name=delete]");
		//일괄 변경 :: 셀렉팅을 한 결과는 제이쿼리의 객체로 받아올 수 있음
		checkbox.prop("checked",checked);
		
		/*
		//선택 반전
		checkbox.each(function(index,checkbox){
			//제이쿼리가 가지고 있는 html 태그를 줘서 프로퍼티를 바꿔줌
			var checked = $(checkbox).prop("checked");
			$(checkbox).prop("checked",!checked);
		});*/
	});
	
	$("input[type=checkbox][name=delete]").change(function(){
		var checkedLength = $("input[type=checkbox][name=delete]:checked").length;
	
		var checkedboxLength = $("input[type=checkbox][name=delete]").length;
		
		if(checkedLength == checkedboxLength){
			$("#toggle").prop("checked", true);
		}else{
			$("#toggle").prop("checked", false);
		}
	});
	
	$("#massDeleteBtn").click(function(){
		$("#massDeleteForm").attr({
			"method":"post",
			"action":"<c:url value="/mypage/communities/delete"/>"
		}).submit();
	});
	
});
</script>
</head>
<body>
	<div id="popup-wrapper">
		<h1 class="title">추억</h1>
		<div>
			<table class="grid" style="width: 100%">
				<colgroup>
					<col style="width: 5%" />
					<col style="width: 90%" />
					<col style="width: 5%" />

				</colgroup>
				<thead>
					<tr>
						<th>ID</th>
						<th>TITLE</th>
						<th><input type="checkbox" id="toggle" /></th>
					</tr>
				</thead>
				<tbody>
					<form id="massDeleteForm">
						<c:forEach items="${myCommunities}" var="mine">
							<tr>
								<td>${mine.id}</td>
								<td>${mine.title}</td>
								<td><input type="checkbox" name="delete" value="${mine.id}" /></td>
							</tr>
						</c:forEach>
					</form>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="3">
							<input type="button" id="massDeleteBtn" value="일괄삭제"/>
						</td>
					</tr>
				</tfoot>

			</table>
		</div>

	</div>
</body>
</html>