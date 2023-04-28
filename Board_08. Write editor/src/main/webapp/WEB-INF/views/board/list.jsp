<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/header.jsp" />

<script type="text/javascript">
$(function() {
	$("#btnWrite").click(function() {
		location.href = "./write"
	})
})
</script>

<div class="container">

<h1>게시글 목록</h1>
<hr>

<table class="table table-striped table-hover table-sm">
<thead class="table-dark">
<tr>
	<th>글번호</th>
	<th>제목</th>
	<th>아이디</th>
	<th>조회수</th>
	<th>작성일</th>
</tr>
</thead>

<c:forEach var="board" items="${list }">
<tr>
	<td>${board.boardno }</td>
	<td>
		<a href="./view?boardno=${board.boardno }">
			<c:choose>
				<c:when test="${empty board.title }">
					(제목없음)
				</c:when>
				<c:otherwise>
					${board.title }
				</c:otherwise>
			</c:choose>
		</a>
	</td>
	<td>${board.userid }</td>
	<td>${board.hit }</td>
	<td>
		<fmt:formatDate value="${board.writeDate }" pattern="yyyy/MM/dd HH:mm:ss"/>
	</td>
</tr>
</c:forEach>

</table>

<!-- 글쓰기 버튼 -->
<div class="float-end mb-3">
	<button id="btnWrite" class="btn btn-primary">글쓰기</button>
</div>
<div class="clearfix"></div>

</div><!-- div.container -->

<c:import url="../layout/paging.jsp" />

<c:import url="../layout/footer.jsp" />



















