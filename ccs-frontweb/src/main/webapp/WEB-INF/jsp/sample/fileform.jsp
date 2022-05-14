<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<c:url value="/login" var="loginUrl" />
	<div class="col-md-12">
		<div class="col-md-6">
		<form class="form-group contents" id="sample-fileForm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		    <input type="hidden" id="fileGrpId" name="fileGrpId"/> 
		    <div class="form-group">
		        <label for="regDt" class="form-label mt-4">등록일자</label>
		        <input type="text" id="regDt" name="regDt" class="form-control datePicker-control">
		    </div>
		    <div class="form-group">
		        <label for="title" class="form-label mt-4">제목</label>
		        <input type="text" id="title" name="title" class="form-control"/>
		    </div>
		    <div class="form-group">
		        <label for="contents" class="form-label mt-4">내용</label>
		        <textarea id="contents" name="contents" class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label><input type="checkbox" name="color" value="blue"> Blue</label>
      			<label><input type="checkbox" name="color" value="red"> Red</label>
			</div>
			<div class="form-group">
				<div id="sample-fileList" class="sta-multifile-upload-list"></div>
			</div>
			<div class="form-group">
                <span id="sample-addFileBtn" class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
                   <span>파일선택</span>
                   <input type="file" id="sample-fileInput" class="file-upload" />            
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
	let $regDtPicker;
	
	this.initialize = function() {
		$this.initData();
		
		$regDtPicker = $("#regDt").datepicker({
	    	language: 'ko'
	    }); 
		
		$("#btn-save").click(function(){
			$this.formManager.save();
		});
		
		// 첨부파일 추가 이벤트
		$("#sample-fileInput").MultiFile({
			accept: "pdf|jpg|jpeg|gif|png|bmp|zip|txt|xlsx|hwp|doc|docx|csv|",
			max: 20,
			list: "#sample-fileList",	// upload / or selected files
			onFileSelect: function(element, value, master_element) {
				console.log(master_element);
			}
		});
		
	}
	
	this.initData = function() {
		let formId = "${formId}";
		if(formId){
			$this.formManager.getData(formId);			
		}
	}
	
	this.formManager = {
		getData : function(formId){
			$.ccs.ajax({
				url : "/sample/fileform/selectData"
				, data : {"formId" : formId}
				, success : function(data){
					$this.formManager.setData(data);
				}
			});
		},
		setData : function(data){
			//form 초기화
			$("#sample-fileForm")[0].reset();
			//form 정보
			$("#sample-fileForm").bindJson(data['form']);
			// 첨부파일 정보
			if (data['files']) {
	            $.ccs.bindFile("sample", data['files']);
			}
		},
		save : function() {
			$.ccs.ajax({
				url : "/sample/fileform/save"
				, fileform : '#sample-fileForm'
				, data : this.getSaveData()
				, success : function(data){
					alert("저장되었습니다.");
					let formId = data['formId'];
					location.replace("/sample/fileform/" + formId);
				}
			});
		},
		getSaveData : function() {
			let jsonData = $(".contents").serializeObject();
			// 기존에 있던 파일이 삭제 되었을 겨웅의 정보 담기.
			jsonData['deleteFiles'] = $("#sample-fileInput").MultiFile("toDeletedList");
			return jsonData;
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>