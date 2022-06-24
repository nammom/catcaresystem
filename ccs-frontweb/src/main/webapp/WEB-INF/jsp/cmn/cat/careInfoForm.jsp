<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<div class="col-md-6">
		<form class="form-group contents" id="careInfo-form" method="post">
			<input type="hidden" 
				id="catcare_cd" name="catcare_cd" 
				value="<c:out value="${catcare_cd}" />"/>
			<input type="hidden" 
				id="s_user_cd" name="s_user_cd" 
				value="<c:out value="${_SESSION_USER_CD_}" />"/>
			<div class="form-group">
		        <label for="nickname" class="form-label mt-4">신청자</label>
		        <input type="text" 
			        id="nickname" name="nickname" 
			        class="form-control" 
			        value="<c:out value="${_SESSION_USER_NM_}" />"
			        disabled/>
		    </div>
		    <div class="form-group">
		        <label for="sub_dt" class="form-label mt-4">신청일</label>
		        <input type="text" 
			        id="sub_dt" name="sub_dt" 
			        class="form-control datePicker-control startDate" 
			        readonly disabled>
		    </div>
		    <div class="form-group">
		        <label for="cat_cd" class="form-label mt-4">고양이 고유코드</label>
		        <input type="text" 
			        id="cat_cd" name="cat_cd" 
			        class="form-control" 
			        value="<c:out value="${cat_cd}" />" 
			        disabled/>
		    </div>
			<div class="form-group">
				<label for="care_type" class="form-label mt-4">구분</label>
				<select id="care_type" name="care_type" 
				 	class="form-control tree-select" 
				 	<c:if test="${not empty catcare_cd}">disabled</c:if> required>
					<option value="E">종료</option>
					<option value="S">재개</option>
				</select>
			</div>
			<div class="form-group">
				<label for="reason_cd" class="form-label mt-4" data-code="C0002" >사유</label>
				<select id="reason_cd" name="reason_cd"  
					class="form-control tree-select" 
					<c:if test="${not empty catcare_cd}">disabled</c:if> required>
					<ccs:option type="option" code="C0002"/>
				</select>
			</div>
		    <div class="form-group">
		        <label for="reason_detail" class="form-label mt-4">상세설명</label>
		        <textarea id="reason_detail" name="reason_detail" 
		        	class="form-control" 
		        	<c:if test="${not empty catcare_cd}">disabled</c:if> required>
		        </textarea>
			</div>
			<button type="button" id="btn-list" class="btn btn-warning">목록</button>
			<c:if test="${not empty catcare_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">수정</button>
		    	<button type="button" id="btn-delete" class="btn btn-warning">삭제</button>
		    </c:if>
		    <c:if test="${empty catcare_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">저장</button>
		    </c:if>
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
	let PAGE_URL = "/cat/careInfo/form";
	let CAT_CD, CATCARE_CD, S_USER_CD;
	
	this.initialize = function() {
		$this.initData();
		
		$("#btn-save").click(function(){
			$this.formManager.save();
		});
		
		$("#btn-delete").click(function(){
			$this.formManager.deleteData();
		});
		
		$("#btn-list").click(function(){
			$this.actionManager.list();
		});
	}
	
	this.initData = function() {
		CATCARE_CD = $("#catcare_cd").val();
		CAT_CD = $("#cat_cd").val();
		S_USER_CD = $("#s_user_cd").val();
		
		if(CATCARE_CD){
			$this.formManager.getData(CATCARE_CD);			
		}else{
			//트리계층 select option생성
			$this.formManager.setSelect();
		}
	}
	
	this.formManager = {
		getData : function(catcare_cd){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {"catcare_cd" : Number(catcare_cd)}
				, success : function(data){
					$this.formManager.setData(data);
				}
			});
		},
		setData : function(data){
			//form 초기화
			$("#careInfo-form")[0].reset();
			//form 정보
			$("#careInfo-form").bindJson(data);
			//트리계층 select option생성
			$this.formManager.setSelect();
			
			if(S_USER_CD == data['user_cd']){
				$("#reason_cd").attr("disabled", false);
				$("#reason_detail").attr("disabled", false);
			}else{
				$("#btn-save").hide();
				$("#btn-delete").hide();
			}
		},
		setSelect : function() {
			//트리계층 select option생성
			$.ccs.cmnCodeOption.getCmnCodeList("#care_type");
		},
		validate : function(){
			return $("#careInfo-form").valid();
		},
		save : function() {
			if($this.formManager.validate()){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, data : this.getSaveData()
					, success : function(data){
						alert("저장되었습니다.");
						let catcare_cd = data['catcare_cd'];
						$this.actionManager.detail(catcare_cd);	
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#careInfo-form").serializeObject();
			jsonData['cat_cd'] = Number(CAT_CD);
			if(jsonData['catcare_cd']){
				jsonData['catcare_cd'] = Number(jsonData['catcare_cd']);
			}
			return jsonData;
		},
		deleteData : function() {
			if($this.formManager.validate()){
				$.ccs.ajax({
					url : PAGE_URL + "/delete"
					, data : this.getSaveData()
					, success : function(data){
						alert("삭제되었습니다.");
						$this.actionManager.list();			
					}
				});
			}
		}
	}
	
	this.actionManager = {
		list : function() {
			location.href = "/cat/careInfo/" + CAT_CD;	
		},
		detail : function(_catcare_cd) {
			location.href = "/cat/careInfo/detail/" + _catcare_cd;				
		}
	}
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>