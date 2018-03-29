<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ActionHistorySearch</title>
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(
			function() {
				$("#searchBtn").click(function() {
					movePage("0");
				});

				//2. 각 달에 따른 마지막 일 확인
				$("#startDateYear, #startDateMonth").change(
						function() {
							//클릭된 애가 this로 넘어올 거임
							var data = $(this);
							if (data.attr("id") == "startDateYear") {
								var year = data.val();
								var month = $("#startDateMonth").val();
							}//month가 바꼈을 때
							else {
								var year = $("#startDateYear").val();
								var month = data.val();
							}

							$.post("<c:url value="/api/date/max/"/>" + year
									+ "/" + month, {}, function(response) {
								$("#startDateDate").find("option").remove();
								setDate("#startDateDate", parseInt(response));
							});
						});

				$("#endDateYear, #endDateMonth").change(
						function() {
							var data = $(this);
							if (data.attr("id") == "endDateYear") {
								var year = data.val();
								var month = $("#endDateMonth").val();
							} else {
								var year = $("#endDateYear").val();
								var month = data.val();
							}
							$.post("<c:url value="/api/date/max/"/>" + year
									+ "/" + month, {}, function(response) {
								$("#endDateDate").find("option").remove();
								setDate("#endDateDate", parseInt(response));
							});
						});

				//1. 검색 날짜 셋팅 : 6년 전의 데이터를 검색할 수 있게
				setYear("#startDateYear",
						parseInt("${search.startDateYear-6}"),
						parseInt("${search.startDateYear}"));
				setYear("#endDateYear", parseInt("${search.endDateYear-6}"),
						parseInt("${search.endDateYear}"));

				setMonth("#startDateMonth");
				setMonth("#endDateMonth");

				setDate("#startDateDate", parseInt("${startDateMaximumDate}"));
				setDate("#endDateDate", parseInt("${endDateMaximumDate}"));

				//현재 날짜로 셋팅
				$("#startDateYear").val("${search.startDateYear}");
				$("#startDateMonth").val("${search.startDateMonth}");
				$("#startDateDate").val("${search.startDateDate}");

				$("#endDateYear").val("${search.endDateYear}");
				$("#endDateMonth").val("${search.endDateMonth}");
				$("#endDateDate").val("${search.endDateDate}");

				function setYear(elementId, startYear, endYear) {
					for (var i = startYear; i <= endYear; i++) {
						$(elementId).append($("<option>", {
							value : i,
							text : i
						}));
					}
				}
				function setMonth(elementId) {
					for (var i = 1; i < 13; i++) {
						var value = (i < 10) ? "0" + i : i;
						$(elementId).append($("<option>", {
							value : value,
							text : value
						}));
					}
				}
				function setDate(elementId, maximumdate) {
					for (var i = 1; i <= maximumdate; i++) {
						var value = (i < 10) ? "0" + i : i;
						$(elementId).append($("<option>", {
							value : value,
							text : value
						}));
					}
				}
			});
</script>
</head>
<body>
	<h2>ACTION HISTORY SEARCH</h2>
	<div>
		<form id="searchForm">
			<div>
				요청종류 <select id="requestType" name="requestType">
					<option value="">전체</option>
					<option value="mbr">Member</option>
					<option value="com">Community</option>
					<option value="view">View</option>
					<option value="upl">Upload</option>
					<option value="dwn">Download</option>
				</select> IP <input type="text" id="ip" name="ip" />
			</div>
			<div>
				EMAIL <input type="text" id="email" name="email" />
			</div>
			<div>
				NICKNAME <input type="text" id="nickname" name="nickname" />
			</div>
			<div>
				LOG <input type="text" id="log" name="log" />
			</div>
			<div>
				AS IS <input type="text" id="asIs" name="asIs" />
			</div>
			<div>
				TO BE <input type="text" id="toBe" name="toBe" />
			</div>
			<div>
				기간 <select id="startDateYear" name="startDateYear">
				</select> - <select id="startDateMonth" name="startDateMonth">
				</select> - <select id="startDateDate" name="startDateDate">
				</select> ~ <select id="endDateYear" name="endDateYear">
				</select> - <select id="endDateMonth" name="endDateMonth">
				</select> - <select id="endDateDate" name="endDateDate">
				</select>
			</div>
			<div>
				<input type="button" id="searchBtn" value="검색" />
			</div>
			<table>
				<c:forEach items="${explorer.list }" var="history">
					<tr>
						<td>${history.histId }</td>
						<td>${history.histDate}</td>
						<td>${history.reqType}</td>
						<td>${history.ip}</td>
						<td>${history.log}</td>
					</tr>
				</c:forEach>
			</table>
			${explorer.make() }
		</form>
	</div>
</body>
</html>