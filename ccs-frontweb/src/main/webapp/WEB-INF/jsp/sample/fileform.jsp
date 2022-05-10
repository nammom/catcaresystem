<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<c:url value="/login" var="loginUrl" />
	<div class="col-md-12">
		<div class="col-md-6">
		<form class="form-group contents" id="history-fileForm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		    <div class="form-group">
		        <label for="title" class="form-label mt-4">제목</label>
		        <input type="text" id="title" name="title" class="form-control"/>
		    </div>
		    <div class="form-group">
		        <label for="contents" class="form-label mt-4">내용</label>
		        <textarea id="contents" name="contents" class="form-control"></textarea>
			</div>
			<div class="form-group">
				<div id="history-fileList" class="sta-multifile-upload-list"></div>
			</div>
			<div class="form-group">
                <span id="history-addFileBtn" class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
                   <span>파일선택</span>
                   <input type="file" id="history-fileInput" class="file-upload" />            
               </span> 
			</div>
		    <button type="button" id="btn-save" class="btn btn-warning">저장</button>
		</form>
		</div>
	</div>
</div>
<script>
let _mypage = null;

$(document).ready(function() {

	_mypage = new fn_page();
	_mypage.initialize();
});

function fn_page() {
	let $this = this;
	this.initialize = function() {
	
		$("#btn-save").click(function(){
			$this.formManager.save();
		});
		
		// 첨부파일 추가 이벤트
		$("#history-fileInput").MultiFile({
			accept: "pdf|jpg|jpeg|gif|png|bmp|zip|txt|xlsx|hwp|doc|docx|csv|",
			max: 20,
			list: "#history-fileList",	// upload / or selected files
			onFileSelect: function(element, value, master_element) {
				console.log(master_element);
			}
		});
		
	}
	
	this.formManager = {
		save : function() {
			$.ccs.ajax({
				url : "/sample/fileform/save"
				, fileform : '#history-fileForm'
				, data : this.getData()
				, success : function(){
					alert("저장되었습니다.");
				}
			});
		},
		getData : function() {
			let jsonData = $(".contents").serializeObject();
			// 기존에 있던 파일이 삭제 되었을 겨웅의 정보 담기.
			jsonData['deleteFiles'] = $("#schedule-fileInput").MultiFile("toDeletedList");
			return jsonData;
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>