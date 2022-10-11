<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<div class="col-md-10">
			<form class="form-group contents" id="cat-form" method="post"
				enctype="multipart/form-data" accept-charset="UTF-8">
				<input type="hidden" id="s_user_cd" name="s_user_cd" value="<c:out value="${_SESSION_USER_CD_}" />"/>
				<input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
				<input type="hidden" id="file_grp_id" name="file_grp_id" />
		
		    	<div class="form-group">
					<label for="reg_dt" class="form-label mt-4">등록일자</label> 
					<input type="text" 
						id="reg_dt" name="reg_dt"
						class="form-control datePicker-control" 
						required disabled>
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
	         		<label for="group_yn" class="form-label">구분</label>
	        	</div>
	        	<div class="form-group ">
					<select id="group_yn" name="group_yn" class="form-control" <c:if test="${not empty cat_cd}">disabled</c:if>>
	                	<option value="N" >개별</option>
	                	<option value="Y" >무리</option>
	                </select> 
				</div>
	        	<div class="row">
					<div class="form-group col-md-1">
						<label for="sido" class="form-label">지역</label>
					</div>
					<div class="form-group col-md-3">
						<select id="sido_cd" name="sido_cd" data-code="sido" class="form-control area-select" caption="지역" required>
		                	<ccs:area code="sido" />
		                </select>
					</div>
					<div class="form-group col-md-3">
						<select id="sigungu_cd" name="sigungu_cd" data-code="sigungu" class="form-control area-select" caption="지역" required>
		                	<ccs:area code="sigungu"/>
		                </select>
					</div>
					<div class="form-group col-md-3">
						<select id="dong_cd" name="dong_cd" data-code="dong" class="form-control area-select" caption="지역">
		                	<ccs:area code="dong" includeAll="전체" /> 
		                </select> 
					</div>
				</div>
		    	<div id="single-cat-form">
		    		<div class="form-group">
		         		<label for="catkind_cd" class="form-label">품종</label>
		        	</div>
			    	<div class="form-group">
			    		<select id="catkind_cd" name="catkind_cd" class="form-control">
							<ccs:option type="option" code="C0003"  includeAll="알 수 없음" />
						</select>
					</div>
			    	<div class="form-group">
		         		<label for="gender" class="form-label">성별</label>
		        	</div>
			    	<div class="form-group">
			    		<select id="gender" name="gender" class="form-control">
			    			<option value="" >알수없음</option>
							<option value="F" >암컷</option>
		                	<option value="M" >수컷</option>
						</select>
					</div>
			    	<div class="form-group">
				        <label class="form-label mt-4" for="age">추정 나이</label>
				        <input type="number" id="age" name="age" class="form-control" value="0" min="0" max="20" />
				    </div>
				    <div class="form-group">
		         		<label for="neutering_yn" class="form-label">중성화 여부</label>
		        	</div>
			    	<div class="form-group">
			    		<select id="neutering_yn" name="neutering_yn" class="form-control">
			    			<option value="" >알수없음</option>
							<option value="Y" >예</option>
		                	<option value="N" >아니오</option>
						</select>
					</div>
		    	</div>
				<div class="form-group">
					<label for="introduction" class="form-label mt-4">특징</label>
					<textarea id="introduction" name="introduction" class="form-control" maxlength="500"></textarea>
				</div>
				<div class="form-group">
					<label class="form-label mt-4">고양이 사진</label>
					<div id="cat-fileList" class="sta-multifile-upload-list"></div>
				</div>
				<div class="form-group">
					<span id="cat-addFileBtn"
						class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
						<span>파일선택</span> 
						<input type="file" 
							id="cat-fileInput"
							class="file-upload" 
							/>
					</span>
				</div>
			</form>
			<button type="button" id="btn-list" class="btn btn-warning">목록</button>
			<c:if test="${not empty cat_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">수정</button>
		    	<button type="button" id="btn-delete" class="btn btn-warning">삭제</button>
		    </c:if>
		    <c:if test="${empty cat_cd}">
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
	let PAGE_URL = "/manage/cat/form";
	let CAT_CD, S_USER_CD, USER_CD;
	
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
		
		//구분 변경
		$("#group_yn").change(function(){
			$this.formManager.setForm($(this).val());
		});
		
		// 첨부파일 추가 이벤트
		$("#cat-fileInput").MultiFile({
			accept : "jpg|jpeg|gif|png",
			max : 1,
			list : "#cat-fileList", // upload / or selected files
			onFileSelect : function(element, value, master_element) {
				console.log(master_element);
			}
		});
	}
	
	this.initData = function() {
		CAT_CD = $("#cat_cd").val()? Number($("#cat_cd").val()) : "";
		S_USER_CD = $("#s_user_cd").val();
		
		//등록일자를 오늘 일자로 설정
		let regDatepicker = $("#reg_dt").datepicker().data("datepicker");
		regDatepicker.selectDate(new Date());
		
		if(CAT_CD){
			$this.formManager.getData();			
		}else {
			$this.formManager.setSelect();
		}
	}
	
	this.formManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {"cat_cd" : CAT_CD}
				, success : function(data){
					$this.formManager.setData(data);
				}
			});
		},
		setData : function(data){
			//form 초기화
			$("#cat-form")[0].reset();
			//form 정보
			$("#cat-form").bindJson(data);
			// 첨부파일 정보
			if (data['fileList']) {
				$.ccs.file.bindFile("cat", data['fileList']);
			}
			//지역 option 셋팅
			this.setSelect();
			//폼 셋팅
			this.setForm(data['group_yn']);
			//USER_CD set
			USER_CD = $("#user_cd").val();

			if(S_USER_CD != USER_CD){
				$("#btn-save").hide();
				$("#btn-delete").hide();
				$("#btn-search-address").hide();
				$("#cat-addFileBtn").hide();
			}
		},
		setForm : function(group_yn) {
			if (group_yn == 'Y') {
				$("#single-cat-form").hide();
			} else {
				$("#single-cat-form").show();
			}
		},
		setSelect : function() {
			//트리계층 select option생성
			$.ccs.cmnCodeOption.getAreaCodeList("#sido_cd");
		},
		validate : function(data){
			if(!$("#cat-form").valid()){
				 return false;
			}
			if(!$.ccs.file.validate("cat", 1)){
				alert("고양이 사진은 필수입니다.");
				return false;
			}
			return true;
		},
		save : function() {
			let _data = this.getSaveData();
			if($this.formManager.validate(_data)){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, fileform : '#cat-form'
					, data : _data
					, success : function(data){
						alert("저장되었습니다.");
						$this.locationManager.list();	
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#cat-form").serializeObject();
			if(jsonData['cat_cd']){
				jsonData['cat_cd'] = Number(jsonData['cat_cd']);
			}
			jsonData['age'] = Number(jsonData['age']);
			// 기존에 있던 파일이 삭제 되었을 경우의 정보 담기.
			jsonData['deleteFiles'] = $("#cat-fileInput").MultiFile("toDeletedList");
			return jsonData;
		},
		deleteData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/delete"
				, data : {cat_cd : CAT_CD}
				, success : function(data){
					alert("삭제되었습니다.");
					$this.locationManager.list();			
				}
			});
		}
	}
	
	this.locationManager = {
		list : function() {
			location.href = "/manage/cat";		
			
		}
	}
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>