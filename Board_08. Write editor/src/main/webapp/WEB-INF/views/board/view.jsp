<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/header.jsp" />



<style type="text/css">
div.content {
	min-height: 300px;
}
</style>

<script type="text/javascript">
$(function() {
	$("#btnList").click(function() {
		location.href = "./list";
	})
	
	$("#btnUpdate").click(function() {
		$(location).attr("href", "./update?boardno=${param.boardno }");
	})

	$("#btnDelete").click(function() {
		$(location).attr("href", "./delete?boardno=${viewBoard.boardno }");
	})
})
</script>




<div class="container">

<h1>게시글 상세보기</h1>
<hr>

<table class="table table-bordered">

<tr>
<td class="table-info">글번호</td><td colspan="3">${viewBoard.boardno }</td>
</tr>

<tr>
<td class="table-info">제목</td><td colspan="3">${viewBoard.title }</td>
</tr>

<tr>
<td class="table-info">아이디</td><td>${viewBoard.userid }</td>
<td class="table-info">닉네임</td><td>${writerNick }</td>
</tr>

<tr>
<td class="table-info">조회수</td><td>${viewBoard.hit }</td>
<td class="table-info">추천수</td><td>[ 추후 추가 ]</td>
</tr>

<tr><td class="table-info" colspan="4">본문</td></tr>
<tr>
	<td colspan="4">
		<div class="content">${viewBoard.content }</div>
	</td>
</tr>

</table>

<!-- 첨부파일 -->
<div class="mb-3">
	<c:if test="${not empty boardFile }">
		<a href="../upload/${boardFile.storedname }" download="${boardFile.originname }">
			${boardFile.originname }
		</a>
	</c:if>
</div>

<div class="text-center">
	<button id="btnList" class="btn btn-primary">목록</button>
	
	<c:if test="${userid eq viewBoard.userid }">
	<button id="btnUpdate" class="btn btn-info">수정</button>
	<button id="btnDelete" class="btn btn-danger">삭제</button>
	</c:if>
</div>

</div><!-- div.container -->

<c:import url="../layout/footer.jsp" />



















