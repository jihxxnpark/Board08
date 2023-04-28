<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>

<ul class="pagination justify-content-center">

	<%-- 첫 페이지로 이동 --%>
	<c:if test="${paging.curPage ne 1 }">
	<li class="page-item"><a class="page-link" href="./list">&larr; 처음</a></li>
	</c:if>
	<c:if test="${paging.curPage eq 1 }">
	<li class="page-item disabled"><a class="page-link" href="./list">&larr; 처음</a></li>
	</c:if>
	
	
	
	<%-- 이전 페이징 리스트로 이동 --%>
<%-- 	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.curPage - paging.pageCount }">&laquo;</a></li> --%>
<%-- 	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.endPage - paging.pageCount }">&laquo;</a></li> --%>
	<c:if test="${paging.startPage ne 1 }">
	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.startPage - paging.pageCount }">&laquo;</a></li>
	</c:if>
	<c:if test="${paging.startPage eq 1 }">
	<li class="page-item"><a class="page-link">&laquo;</a></li>
	</c:if>
	
	


	<%-- 이전 페이지로 이동 --%>
	<c:if test="${paging.curPage gt 1 }">
	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.curPage - 1 }">&lt;</a></li>
	</c:if>



	<%-- 페이징 번호 리스트 --%>
	<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
	
		<c:if test="${paging.curPage eq i }">
		<li class="page-item active">
			<a class="page-link" href="./list?curPage=${i }">${i }</a>
		</li>
		</c:if>
		
		<c:if test="${paging.curPage ne i }">
		<li class="page-item">
			<a class="page-link" href="./list?curPage=${i }">${i }</a>
		</li>
		</c:if>
		
	</c:forEach>
	
	
	
	<%-- 다음 페이지로 이동 --%>
	<c:if test="${paging.curPage lt paging.totalPage }">
	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.curPage + 1 }">&gt;</a></li>
	</c:if>




	<%-- 다음 페이징 리스트로 이동 --%>
	<c:if test="${paging.endPage ne paging.totalPage }">
	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.startPage + paging.pageCount }">&raquo;</a></li>
	</c:if>
	<c:if test="${paging.endPage eq paging.totalPage }">
	<li class="page-item"><a class="page-link">&raquo;</a></li>
	</c:if>
	
	

	
	<%-- 마지막 페이지로 이동 --%>
	<c:if test="${paging.curPage ne paging.totalPage }">
	<li class="page-item"><a class="page-link" href="./list?curPage=${paging.totalPage }">마지막 &rarr;</a></li>
	</c:if>
	<c:if test="${paging.curPage eq paging.totalPage }">
	<li class="page-item disabled"><a class="page-link" href="./list?curPage=${paging.totalPage }">마지막 &rarr;</a></li>
	</c:if>
	
	
</ul>

</div>