<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/header.jsp" />

<script type="text/javascript" src="../resources/se2/js/service/HuskyEZCreator.js"></script>

<script type="text/javascript">
$(function() {
	
	//작성버튼 동작
	$("#btnWrite").click(function() {
		
		//제목 필수 입력 설정
		if( $("input[name~='title']").val() == '' ) {
			alert("제목을 입력하세요")
			
			return false;
		}
		
		//작성된 내용을 <textarea>에 적용하기
		updateContents()

		$("form").submit()
	})
	
	//취소버튼 동작
	$("#btnCancel").click(function() {
		history.go(-1)
	})
	
})

function updateContents() {
	//스마트 에디터에 작성된 내용을 textarea#content에 반영한다
	oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", [])
}
</script>

<style type="text/css">
#content {
	width: 98%;
}
</style>

<div class="container">

<h1>게시글 쓰기</h1>
<hr>

<div class="col-10 mx-auto">
<form action="./write" method="post" enctype="multipart/form-data">

<table class="table table-bordered">

<tr>
	<td class="table-info col-3">아이디</td>
	<td class="col-9">${userid }</td>
</tr>

<tr>
	<td class="table-info">닉네임</td>
	<td>${usernick }</td>
</tr>

<tr>
	<td class="table-info align-middle">제목</td>
	<td><input type="text" name="title" class="form-control" placeholder="제목"></td>
</tr>

<tr>
	<td class="table-info" colspan="2">본문</td>
</tr>
<tr>
	<td colspan="2"><textarea id="content" name="content" class="form-control" placeholder="내용을 입력하세요"></textarea>
</table>

<div class="mb-3">
첨부파일
<input type="file" name="file" class="form-control">
</div>

</form>
</div>

<div class="text-center">
	<button type="button" id="btnWrite" class="btn btn-info">작성</button>
	<button type="button" id="btnCancel" class="btn btn-danger">취소</button>
</div>

</div><!-- div.container -->

<script type="text/javascript">
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "content",	//에디터가 적용될 <textarea>의 id속성값
	sSkinURI: "../resources/se2/SmartEditor2Skin.html",
	fCreator: "createSEditor2"
})
</script>

<c:import url="../layout/footer.jsp" />




















