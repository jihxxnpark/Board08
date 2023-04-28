<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/header.jsp" />

<script type="text/javascript" src="../resources/se2/js/service/HuskyEZCreator.js"></script>



<script type="text/javascript">
$(function() {
	
	//작성버튼 동작
	$("#btnUpdate").click(function() {
		
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
	
	
	//파일이 없을 경우
	if(${empty boardFile}){
		$("#befoerFile").hide()
		$("#afterFile").show()
	}
	//파일이 있 경우
	if(${not empty boardFile}){
		$("#befoerFile").show()
		$("#afterFile").hide()
	}
	
	//파일 삭제 버튼(x) 클릭 처리
	$("#delFile").click(function(){
		$("#beforeFile a").toggleClass("text-decoration-line-through")
		
		$("#afterFile").toggle()
		
// 		if($(this).next("input").prop("checked"){
// 			$(this).next("input").prop("checked", false)
// 		}else{
// 			$(this).next("input").prop("checked", true)
// 		}

		$(this).next("input").prop("checked", function(){
			return !$(this).prop("checked")
		})
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
#delFile{
	color: red;
	font-weight: bold;
	cursor: pointer;
}


</style>

<div class="container">

<h1>게시글 수정</h1>
<hr>

<div class="col-10 mx-auto">
<form action="./update" method="post" enctype="multipart/form-data">

<input type="hidden" name="boardno" value="${param.boardno }">

<table class="table table-bordered">

<tr>
	<td class="table-info col-3">아이디</td>
	<td class="col-9">${userid }</td>
</tr>

<tr>
	<td class="table-info">닉네임</td>
	<td>${writerNick }</td>
</tr>

<tr>
	<td class="table-info align-middle">제목</td>
	<td><input type="text" name="title" class="form-control" placeholder="제목" value="${viewBoard.title }"></td>
</tr>

<tr>
	<td class="table-info" colspan="2">본문</td>
</tr>
<tr>
	<td colspan="2"><textarea id="content" name="content" class="form-control" placeholder="내용을 입력하세요">${viewBoard.content }</textarea>
</table>

<div class="mb-3">
	
	<div id="beforeFile">
		<c:if test="${not empty boardFile }">
		기존 첨부파일 :
		<a href="../upload/${boardFile.storedname }" download="${boardFile.originname }">
			${boardFile.originname }
		</a>
		<span id="delFile" class="text-danger fw-bold" role="button">
			<i class="bi bi-trash3-fill"></i>
		</span>
		<input type="checkbox" name="del" value="y" class="d-none">
		</c:if>
	</div>
	
	<div id="afterFile">
		새 첨부파일
		<input type="file" name="file" class="form-control">
	</div>

</div>

</form>
</div>

<div class="text-center">
	<button type="button" id="btnUpdate" class="btn btn-warning">수정 적용</button>
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
