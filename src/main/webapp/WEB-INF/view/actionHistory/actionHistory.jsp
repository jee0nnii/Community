<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ActionHistorySearch</title>
</head>
<body>
	<h2>ACTION HISTORY SEARCH</h2>
	<form id="searchForm">
		<div>
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
					<option></option>
				</select> - <select id="startDateMonth" name="startDateMonth">
					<option></option>
				</select> - <select id="startDateDate" name="startDateDate">
					<option></option>
				</select> ~ <select id="endDateYear" name="endDateYear">
					<option></option>
				</select> - <select id="endDateMonth" name="endDateMonth">
					<option></option>
				</select> - <select id="endDateDate" name="endDateDate">
					<option></option>
				</select>
			</div>
			<div>
				<input type="button" id="searchBtn" value="검색" />
			</div>
		
		</div>
		
	</form>
</body>
</html>