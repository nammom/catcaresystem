<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<div class="container">
<div class="col-md-12">
	<button type="button" id="btn-save" class="btn btn-warning">등록</button>
</div>
<div class="col-md-12">
	<form onsubmit="return false" id="question-form" class="form-group m-3">
	    <input type="hidden" id="questionno" name="questionno" value="<c:out value="${questionno}" />"/>
	    <input type="hidden" id="file_grp_id" name="file_grp_id" />

		<div class="form-group">
	       	<label for="nickname" class="form-label mt-4">제목</label>
	        <input type="text" 
	        	id="title" name="title" 
	        	data-validation="Y"
	        	class="form-control"
	        	maxlength="30"
	        	required
	        	/>
		</div>
		<div class="form-group">
			<label for="content" class="form-label mt-4">상세설명</label>
			<textarea id="content" name="content" class="form-control" maxlength="1300" required></textarea>
		</div>
		<div class="form-group">
			<span id="question-addFileBtn"
				class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
				<span>파일선택</span> 
				<input type="file" 
					id="question-fileInput"
					class="file-upload with-preview" 
					/>
			</span>
		</div>
		<div class="form-group">
			<label class="form-label mt-4">첨부파일</label>
			<div id="question-fileList" class="sta-multifile-upload-list"></div>
		</div>
	</form>	    
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
	let PAGE_URL = "/servicecenter/question/form";
	
	this.$table;
	this.initialize = function() {
		$this.initData();
		
		$("#btn-save").click(function(){
			$this.formManager.save();
		});
		
		// 첨부파일 추가 이벤트
		$("#question-fileInput").MultiFile({
			accept : "jpg|jpeg|gif|png",
			max : 3,
			list : "#question-fileList", // upload / or selected files
			onFileSelect : function(element, value, master_element) {
				console.log(master_element);
			}
		});
	}
	
	this.initData = function() {
		//USER_CD = $("#questionno").val()? Number($("#questionno").val()) : null;
		//$this.formManager.getData();
		
	}
	
	this.formManager = {
		validate : function(){
			if(!$("#question-form").valid()){
				 return false;
			}
			return true;
		},
		save : function(callBackFunc) {
			let _data = this.getSaveData();
			if($this.formManager.validate(_data)){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, fileform : '#question-form'
					, data : _data
					, success : function(data){
						alert("저장되었습니다.");
						$this.locationManager.list();	
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#question-form").serializeObject();
			if(jsonData['questionno']){
				jsonData['questionno'] = Number(jsonData['questionno']);
			}
			// 기존에 있던 파일이 삭제 되었을 경우의 정보 담기.
			jsonData['deleteFiles'] = $("#question-fileInput").MultiFile("toDeletedList");
			return jsonData;
		}
		
	}
	
	this.locationManager = {
			list : function(){
				location.href = "/servicecenter/question";
			}
		}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>