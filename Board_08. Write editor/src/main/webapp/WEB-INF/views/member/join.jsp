<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/header.jsp" />


<script type="text/javascript">
$(function() {
	//페이지 접속 시 아이디 입력창으로 포커스 이동
	$("input").eq(0).focus();
	
	//닉네임 입력 창에서 엔터키 입력 시 submit
	$("input").eq(2).keydown(function(e) {
		if( e.keyCode == 13 ) { //엔터키
			$(this).parents("form").submit();
		}
	})

	//회원가입 버튼 클릭시 submit
	$("#btnJoin").click(function() {
		$(this).parents("form").submit();
	})
	
	//취소 버튼 클릭 시 동작
	$("#btnCancel").click(function() {
		history.go(-1)	//뒤로가기
// 		location.href = "/"	//메인으로 가기
	})
	
})
</script>



<div class="container">

<h1>회원 가입</h1>
<hr>

<div class="col-8 mx-auto">

<form action="./join" method="post">

<div class="row mb-3">
	<label class="col-form-label col-3" for="userid">아이디</label>
	<div class="col-9">
		<input type="text" class="form-control" id="userid" name="userid">
	</div>
</div>

<div class="row mb-3">
	<label class="col-form-label col-3" for="userpw">패스워드</label>
	<div class="col-9">
		<input type="text" class="form-control" id="userpw" name="userpw">
	</div>
</div>

<div class="row mb-3">
	<label class="col-form-label col-3" for="usernick">닉네임</label>
	<div class="col-9">
		<input type="text" class="form-control" id="usernick" name="usernick">
	</div>
</div>

<div class="text-center">
	<button type="button" id="btnJoin" class="btn btn-primary">회원가입</button>
	<button type="button" id="btnCancel" class="btn btn-danger">취소</button>
</div>

</form>

</div>

</div><!-- div.container -->

<c:import url="../layout/footer.jsp" />



















