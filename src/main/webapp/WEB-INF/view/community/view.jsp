<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${community.title}</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/alert.css"/>"/>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script src="<c:url value="/static/js/alert.js"/>" type="text/javascript"></script>
<script type="text/javascript">
$().ready(function(){
	
	loadReplies(0);
	function loadReplies(scrollTop){
	//url이 같아서 get
	$.get("<c:url value="/api/reply/${community.id}"/>",{},function(response){
		for(var i in response){
			appendReplies(response[i]);	
		}
		$(window).scrollTop(scrollTop);
	});
	}
	
	$("#writeReplyBtn").click(function(){
		console.log($("#writeReplyForm").serialize());
		$.post("<c:url value="/api/reply/${community.id}"/>",
				$("#writeReplyForm").serialize(),
				function(response){
			/* 0323 */
					if(response.status){
						show("댓글등록됨");
						
						$("#parentReplyId").val("0");
						$("#body").val("");
						
						$("#createReply").appendTo("#createReplyDiv");
						
						var scrollTop = $(window).scrollTop();
						
						//appendReplies(response.reply);						
						$("#replies").html("");
						loadReplies(scrollTop);
						
					}else{
						alert("등록에 실패하였습니다. 다시 등록하세요.");
					}
			/* 
			alert("등록됨");
			console.log(response); */
		});
		
	});
	
	/* 만들어진 shadow dom에 접근하기 */
	$("#replies").on("click",".re-reply", function(){
		var parentReplyId = $(this).closest(".reply").data("id");
		$("#parentReplyId").val(parentReplyId);
		
		$("#createReply").appendTo($(this).closest(".reply"));
		
	});
	
	
	
	function appendReplies(reply){
		var replyDiv = $("<div class ='reply' style='padding-left:"+ ( (reply.level-1) * 20 )+"px;' data-id='"+reply.id+"'></div>");
		var nickname = reply.memberVO.nickname + "(" + reply.memberVO.email + ")";
		
		var top = $("<span class = 'writer'>" + nickname +"</span><span class = 'regist-date'>"+reply.registDate+"</span>")
		replyDiv.append(top);
		
		var body = $("<div class = 'body'>" + reply.body +"</div>");
		replyDiv.append(body);
		
		var registReReply = $("<div class ='re-reply'>댓글달기</div>");
		replyDiv.append(registReReply);
		
	/* 	if(reply.parentReplyId == 0){ */
			$("#replies").append(replyDiv);
	/* 	}
		else{
			$(".reply").each(function(index, data){
//				console.log(data);
				var replyId = $(data).data("id");
				if ( reply.parentReplyId == replyId ){
					$(data).after(replyDiv);
				}
			});
		} */
		
/* 0323 */	
	}
});
</script>
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
		
		<hr>
		<!-- 0322 댓글 -->
		<div id ="replies"></div>
		<div id = "createReplyDiv">
		<div id ="createReply">
			<form id="writeReplyForm">
				<input type="hidden" id="parentReplyId" name="parentReplyId" value="0"/>
				<div>
					<textarea id="body" name="body"></textarea>
				</div>
				<div>
					<input type="button" id="writeReplyBtn" value="등록"/>
				</div>
			</form>
		</div>
		<!-- 0322 댓글 -->
		</div>
		
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