<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="./layout/header.jsp" />

<div class="container">

<%-- 비로그인상태: ${empty login }<br> --%>
<%-- 로그인상태 : ${not empty login and login} --%>

<div class="text-center">
	<c:if test="${empty login }">
	<button class="btn btn-primary" onclick="location.href='./member/login'">로그인</button>
	<button class="btn btn-success" onclick="location.href='./member/join'">회원가입</button>
	</c:if>
	
	<c:if test="${not empty login and login}">
	<button class="btn btn-info" onclick="location.href='./board/list'">게시판 가기</button>
	<button class="btn btn-warning" onclick="location.href='./member/logout'">로그아웃</button>
	</c:if>
</div>

</div><!-- div.container -->

<c:import url="./layout/footer.jsp" />



















