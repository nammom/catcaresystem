<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<%@ include file="/WEB-INF/jsp/cmn/menu/manageNav.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<div class="col-md-6">
			<form class="form-group contents" id="health-form" method="post"
				enctype="multipart/form-data" accept-charset="UTF-8">
				<input type="hidden" id="health_cd" name="health_cd" value="<c:out value="${health_cd}" />">
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
		    	<div class="row">
					<div class="form-group col-md-1">
						<label for="disease" class="form-label">질병</label>
					</div>
					<div class="form-group col-md-3">
						<select id="disease_cd" name="disease_cd" data-code="C0001" 
						class="form-control tree-select" 
						<c:if test="${not empty health_cd}">disabled</c:if>>
		                	<ccs:option type="option" code="C0001" prntCode="0" includeAll="없음" />
		                </select>
					</div>
					<div class="form-group col-md-3">
						<select id="disease_cd2" name="disease_cd2" data-code="C0001" 
						class="form-control tree-select" 
						<c:if test="${not empty health_cd}">disabled</c:if>>
		                	<ccs:option type="option" code="C0001"  includeAll="전체" />
		                </select>
					</div>
					<div class="form-group col-md-3">
						<select id="disease_cd3" name="disease_cd3" data-code="C0001" 
						class="form-control tree-select" 
						<c:if test="${not empty health_cd}">disabled</c:if>>
		                	<ccs:option type="option" code="C0001" includeAll="전체" /> 
		                </select> 
					</div>
				</div>
		    	<div class="form-group">
					<label for="disease_detail" class="form-label mt-4">상세설명</label>
					<textarea id="disease_detail" name="disease_detail" class="form-control" maxlength="500" 
					<c:if test="${not empty health_cd}">disabled</c:if>></textarea>
				</div>
				<div class="form-group">
					<div id="health-fileList" class="sta-multifile-upload-list"></div>
				</div>
				<div class="form-group">
					<span id="health-addFileBtn"
						class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
						<span>파일선택</span> 
						<input type="file" 
							id="health-fileInput"
							class="file-upload" />
					</span>
				</div>
			</form>
			<button type="button" id="btn-list" class="btn btn-warning">목록</button>
			<c:if test="${not empty health_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">수정</button>
		    	<button type="button" id="btn-delete" class="btn btn-warning">삭제</button>
		    </c:if>
		    <c:if test="${empty health_cd}">
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
	let PAGE_URL = "/care/health/form";
	let CAT_CD, HEALTH_CD, S_USER_CD, USER_CD;
	
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
		$("#health-fileInput").MultiFile({
			accept : "jpg|jpeg|gif|png",
			max : 5,
			list : "#health-fileList", // upload / or selected files
			onFileSelect : function(element, value, master_element) {
				console.log(master_element);
			}
		});
	}
	
	this.initData = function() {
		CAT_CD = $("#cat_cd").val()? Number($("#cat_cd").val()) : "";
		HEALTH_CD = $("#health_cd").val()? Number($("#health_cd").val()) : "";
		S_USER_CD = $("#s_user_cd").val();
		
		//급여일자를 오늘 일자로 설정
		let regDatepicker = $("#reg_dt").datepicker().data("datepicker");
		regDatepicker.selectDate(new Date());
		
		if(HEALTH_CD){
			$this.formManager.getData();			
		}else{
			//트리계층 select option생성
			$this.formManager.setSelect();
		}
	}
	
	this.formManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : HEALTH_CD ? {"health_cd" : HEALTH_CD} : {"cat_cd" : CAT_CD}
				, success : function(data){
					if(HEALTH_CD){
						$this.formManager.setData(data);
					}
				}
			});
		},
		setData : function(data){
			//form 초기화
			$("#health-form")[0].reset();
			//form 정보
			$("#health-form").bindJson(data);
			//트리계층 select option생성
			$this.formManager.setSelect();
			// 첨부파일 정보
			if (data['fileList']) {
				$.ccs.file.bindFile("health", data['fileList']);
			}
			//USER_CD set
			USER_CD = $("#user_cd").val();
			//CAT_CD set
			CAT_CD = $("#cat_cd").val()? Number($("#cat_cd").val()) : "";

			if(S_USER_CD == USER_CD){
				$("#disease_cd").attr("disabled", false);
				$("#disease_cd2").attr("disabled", false);
				$("#disease_cd3").attr("disabled", false);
				$("#disease_detail").attr("disabled", false);
			}else{
				$("#btn-save").hide();
				$("#btn-delete").hide();
			}
		},
		setSelect : function() {
			//트리계층 select option생성
			$.ccs.cmnCodeOption.getCmnCodeList("#disease_cd");
		},
		validate : function(data){
			if(!$("#health-form").valid()){
				 return false;
			}
			return true;
		},
		save : function() {
			let _data = this.getSaveData();
			if($this.formManager.validate(_data)){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, fileform : '#health-form'
					, data : _data
					, success : function(data){
						alert("저장되었습니다.");
						$this.locationManager.list();
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#health-form").serializeObject();
			if(jsonData['health_cd']){
				jsonData['health_cd'] = Number(jsonData['health_cd']);
			}
			jsonData['cat_cd'] = Number(jsonData['cat_cd']);
			// 기존에 있던 파일이 삭제 되었을 경우의 정보 담기.
			jsonData['deleteFiles'] = $("#health-fileInput").MultiFile("toDeletedList");
			return jsonData;
		},
		deleteData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/delete"
				, data : {health_cd : HEALTH_CD}
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
				location.href = "/care/health/" + target_type + "/" + target_cd;		
			} else {
				alert("이전 목록이 없습니다. 홈 화면으로 돌아갑니다.");
				location.href = "/home";	
			}
		},
		detail : function(_health_cd) {
			location.href = "/cat/health/detail/" + _health_cd;				
		}
	}
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>