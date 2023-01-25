<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<div class="container">
<div class="col-md-12">
		<button type="button" id="btn-save" class="btn btn-warning">등록</button>
		<button type="button" id="btn-cancel" class="btn btn-secondary">취소</button>
	</div>
<div class="col-md-12">
	<form onsubmit="return false" id="profile-form" class="form-group m-3">
	    <input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
	    <input type="hidden" id="file_grp_id" name="file_grp_id" />
		<div class="form-group">
			<label class="form-label mt-4">프로필 사진</label>
			<div id="profile-fileList" class="sta-multifile-upload-list" style="display:none;"></div>
		</div>
		<div class="form-group">
			<span id="profile-addFileBtn"
				class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
				<span>파일선택</span> 
				<input type="file" 
					id="profile-fileInput"
					class="file-upload"
					/>
			</span>
		</div>
		<div class="form-group">
			<img id="preview" class="img-preview" />
		</div>
		<div class="form-group">
	       	<label for="nickname" class="form-label mt-4">닉네임</label>
	        <input type="text" 
	        	id="nickname" name="nickname" 
	        	data-validation="Y"
	        	class="form-control"
	        	maxlength="10"
	        	required
	        	/>
	        <p id="nicknameResult" class="text-danger"></p>
		</div>
		<div class="form-group">
			<label for="introduction" class="form-label mt-4">소개</label>
			<textarea id="introduction" name="introduction" class="form-control" maxlength="300"></textarea>
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
	let PAGE_URL = "/user/profile/form";
	let USER_CD;
	
	this.$table;
	this.initialize = function() {
		$this.initData();
		
		$("#btn-save").click(function(){
			$this.formManager.save();
		});
		
		$("#btn-cancel").click(function(){
			$this.locationManager.prevPage();
		});
		
		$("#nickname").change(function(){
			if($(this).val()) {
				$this.actionManager.checkNickname($(this).val());
			} else {
				$("#nicknameResult").html("닉네임을 입력해주세요.");
			}
		});

		// 첨부파일 추가 이벤트
		$("#profile-fileInput").MultiFile({
			accept : "jpg|jpeg|gif|png",
			//max : 1,
			list : "#profile-fileList", // upload / or selected files
			onFileSelect : function(element, value, master_element) {
				console.log(master_element);
			},
			onFileAppend: function(element, value, master_element) {
				//기존 이미지 제거 
				$.ccs.file.reset("profile-form");
			},
			afterFileAppend: function(element, value, master_element) {
				//파일 미리보기 이미지 경로 설정
				$.ccs.file.preview(master_element.files[0]);
			}
		});
	}
	
	this.initData = function() {
		USER_CD = $("#user_cd").val()? Number($("#user_cd").val()) : null;
		$this.formManager.getData();
		
	}
	
	this.formManager = {
		getData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {
							"user_cd" : USER_CD
						}
				, success : function(data){
					$this.formManager.setData(data);
				}
			});
		},
		setData : function(data) {
			//form 초기화
			$("#profile-form")[0].reset();
			//form 정보
			$("#profile-form").bindJson(data);
			// 첨부파일 정보
			if (data['fileList']) {
				$.ccs.file.bindFile("profile", data["fileList"]);
				$.ccs.file.bindPreview(data["file_path"]);
			}
		},
		validate : function(){
			if(!$("#profile-form").valid()){
				 return false;
			}
			if($("#nickname").data("validation") != "Y"){
				alert("사용할 수 없는 닉네임 입니다.");
				return false;
			}
			return true;
		},
		save : function(callBackFunc) {
			let _data = this.getSaveData();
			if($this.formManager.validate(_data)){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, fileform : '#profile-form'
					, data : _data
					, success : function(data){
						alert("저장되었습니다.");
						$this.locationManager.prevPage();
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#profile-form").serializeObject();
			if(jsonData['user_cd']){
				jsonData['user_cd'] = Number(jsonData['user_cd']);
			}
			// 기존에 있던 파일이 삭제 되었을 경우의 정보 담기.
			jsonData['deleteFiles'] = $("#profile-fileInput").MultiFile("toDeletedList");
			return jsonData;
		}
		
	}
	
	this.actionManager = {
		checkNickname : function(nickname){
			$.ccs.ajax({
				url :  PAGE_URL + "/checkNickname"
				, data : {
							"nickname" : nickname
						}
				, success : function(data){
					$this.actionManager.checkNicknameCallback(data);
				}
			});
		},
		checkNicknameCallback : function(data){
			if(data.status == "Y"){
				$("#nicknameResult").html("사용가능한 닉네임 입니다.");
				$("#nickname").data("validation", "Y");
			}else{
				$("#nicknameResult").html("이미 사용되고 있는 닉네임 입니다.");
				$("#nickname").data("validation", "N");
			}
		}
	}
	
	this.locationManager = {
		prevPage : function() {
			location.href = "/user/profile/" + USER_CD;				
		}
	}
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>