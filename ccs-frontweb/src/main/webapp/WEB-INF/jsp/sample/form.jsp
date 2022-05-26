<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<c:url value="/login" var="loginUrl" />
	<div class="col-md-12">
		<div class="col-md-6">
		<form:form id="formId" class="form-group contents" onsubmit="return false">
		    <div class="form-group">
		        <label for="username" class="form-label mt-4">아이디</label>
		        <input type="text" id="id" name="id" class="form-control"/>
		    </div>
		    <div class="form-group">
		        <label for="password" class="form-label mt-4">비밀번호</label>
		        <input type="password" id="password" name="password" class="form-control"/>
			</div>
		    <button type="button" id="btn-save" class="btn btn-warning">저장</button>
		    <button type="button" class="btn btn-secondary">취소</button>
		</form:form>
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
		
	}
	
	this.formManager = {
		save : function() {
			$.ccs.ajax({
				url : "/sample/form/save"
				, data : this.getData()
				, success : function(){
					alert("저장되었습니다.");
				}
			});
		},
		getData : function() {
			return $(".contents").serializeObject();
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>