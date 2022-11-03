<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<div class="col-md-6">
			<form onsubmit="return false" class="form-group contents" id="character-form" method="post"
				enctype="multipart/form-data" accept-charset="UTF-8">
				<input type="hidden" id="character_cd" name="character_cd" value="<c:out value="${character_cd}" />">
				<input type="hidden" id="s_user_cd" name="s_user_cd" value="<c:out value="${_SESSION_USER_CD_}" />"/>
				<input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
				<input type="hidden" id="file_grp_id" name="file_grp_id" />
				
				<div class="form-group">
		        	<label for="nickname" class="form-label mt-4">작성자</label>
		        	<input type="text" 
			        	id="nickname" name="nickname" 
			        	class="form-control"
			        	value="<c:out value="${_SESSION_USER_NM_}" />"
			        	disabled/>
		    	</div>
			    <div class="form-group">
		        <label for="cat_cd" class="form-label mt-4">고양이 고유코드</label>
			        <input type="text" 
			        	id="cat_cd" name="cat_cd" 
			        	value="<c:out value="${cat_cd}" />"
			        	class="form-control" 
			        	disabled/>
		    	</div>
		    	<div class="form-group">
		    		<label for="caution_yn" class="form-label mt-4">주의필요</label>
			        <select id="caution_yn" name="caution_yn" class="form-control" <c:if test="${not empty character_cd}">disabled</c:if>>
                		<option value="Y">예</option>
                		<option value="N">아니오</option>
                	</select> 
		    		<p class="text-danger font-weight-bold">공격적이거나 예민한 성격을 가진 고양이의 경우 선택해주세요</p>
		    	</div>
		    	<div class="form-group">
			        <label class="form-label mt-4" for="aggression">공격성</label>
			        <input type="number" id="aggression" name="aggression" class="form-control" value="0" min="0" max="5" <c:if test="${not empty character_cd}">disabled</c:if>>
			    </div>
			    <div class="form-group">
			        <label class="form-label mt-4" for="sensitivity">예민함</label>
			        <input type="number" id="sensitivity" name="sensitivity" class="form-control" value="0" min="0" max="5" <c:if test="${not empty character_cd}">disabled</c:if>>
			    </div>
		    	<div class="form-group">
					<label for="character_detail" class="form-label mt-4">상세설명</label>
					<textarea id="character_detail" name="character_detail" class="form-control" maxlength="500" <c:if test="${not empty character_cd}">disabled</c:if>></textarea>
				</div>
				<div class="form-group">
					<div id="character-fileList" class="sta-multifile-upload-list"></div>
				</div>
				<div class="form-group">
					<span id="character-addFileBtn"
						class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
						<span>파일선택</span> 
						<input type="file" 
							id="character-fileInput"
							class="file-upload" />
					</span>
				</div>
			</form>
			<button type="button" id="btn-list" class="btn btn-warning">목록</button>
			<c:if test="${not empty character_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">수정</button>
		    	<button type="button" id="btn-delete" class="btn btn-warning">삭제</button>
		    </c:if>
		    <c:if test="${empty character_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">저장</button>
		    </c:if>
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
	let PAGE_URL = "/care/character/form";
	let CAT_CD, CHARACTER_CD, S_USER_CD, USER_CD;
	
	this.initialize = function() {
		
		$this.initData();
		
		//폼 등록(수정)
		$("#btn-save").click(function(){
			$this.formManager.save();
		});
		
		//폼 삭제
		$("#btn-delete").click(function(){
			$this.formManager.deleteData();
		});
 		
		//목록으로
		$("#btn-list").click(function(){
			$this.locationManager.list();
		});
		
		// 첨부파일 추가 이벤트
		$("#character-fileInput").MultiFile({
			accept : "jpg|jpeg|gif|png",
			max : 5,
			list : "#character-fileList", // upload / or selected files
			onFileSelect : function(element, value,
					master_element) {
				console.log(master_element);
			}
		});
	}
	
	this.initData = function() {
		CAT_CD = $("#cat_cd").val()? Number($("#cat_cd").val()) : "";
		CHARACTER_CD = $("#character_cd").val()? Number($("#character_cd").val()) : "";
		S_USER_CD = $("#s_user_cd").val();
		
		if(CHARACTER_CD){
			$this.formManager.getData();			
		}
	}
	
	this.formManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : CHARACTER_CD ? {"character_cd" : CHARACTER_CD} : {"cat_cd" : CAT_CD}
				, success : function(data){
					if(CHARACTER_CD){
						$this.formManager.setData(data);
					}
				}
			});
		},
		setData : function(data){
			//form 초기화
			$("#character-form")[0].reset();
			//form 정보
			$("#character-form").bindJson(data);
			// 첨부파일 정보
			if (data['fileList']) {
				$.ccs.file.bindFile("character", data['fileList']);
			}
			//USER_CD set
			USER_CD = $("#user_cd").val();
			//CAT_CD set
			CAT_CD = $("#cat_cd").val()? Number($("#cat_cd").val()) : "";

			if(S_USER_CD == USER_CD){
				$("#caution_yn").attr("disabled", false);
				$("#aggression").attr("disabled", false);
				$("#sensitivity").attr("disabled", false);
				$("#character_detail").attr("disabled", false);
			}else{
				$("#btn-save").hide();
				$("#btn-delete").hide();
			}
		},
		validate : function(data){
			if(!$("#character-form").valid()){
				 return false;
			}
			return true;
		},
		save : function() {
			let _data = this.getSaveData();
			if($this.formManager.validate(_data)){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, fileform : '#character-form'
					, data : _data
					, success : function(data){
						alert("저장되었습니다.");
						$this.locationManager.list();
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#character-form").serializeObject();
			if(jsonData['character_cd']){
				jsonData['character_cd'] = Number(jsonData['character_cd']);
			}
			jsonData['cat_cd'] = Number(jsonData['cat_cd']);
			jsonData['aggression'] = Number(jsonData['aggression']);
			jsonData['sensitivity'] = Number(jsonData['sensitivity']);
			// 기존에 있던 파일이 삭제 되었을 경우의 정보 담기.
			jsonData['deleteFiles'] = $("#character-fileInput").MultiFile("toDeletedList");
			return jsonData;
		},
		deleteData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/delete"
				, data : {character_cd : CHARACTER_CD}
				, success : function(data){
					alert("삭제되었습니다.");
					$this.locationManager.list();			
				}
			});
		}
	}
	
	this.locationManager = {
		list : function() {
			let target_type = window['sessionStorage'].getItem("target_type");
			let target_cd = window['sessionStorage'].getItem("target_cd");
			if(target_type && target_cd) {
				location.href = "/care/character/" + target_type + "/" + target_cd;		
			} else {
				alert("이전 목록이 없습니다. 홈 화면으로 돌아갑니다.");
				location.href = "/home";	
			}
		},
		detail : function(_character_cd) {
			location.href = "/cat/character/detail/" + _character_cd;				
		}
	}
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>