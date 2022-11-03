<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header2.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<form onsubmit="return false" id="catName-form" class="form-group m-3">
		<div class="form-group">
        	<label for="target_cd" class="form-label mt-4">고양이 고유코드</label>
	        <input type="text" 
	        	id="target_cd" name="target_cd" 
	        	value="<c:out value="${target_cd}" />"
	        	class="form-control" 
	        	disabled/>
    	</div>
		<div class="form-group">
	       	<label for="cat_name" class="form-label mt-4">애칭</label>
	        <input type="text" 
	        	id="cat_name" name="cat_name" 
	        	class="form-control" 
	        	/>
		</div>
	</form>	    
</div>
</div>

<script>
$(document).ready(function() {
	if(window['_modal']) window['_modal'] = null;
	window['_modal'] = new fn_modal();
	window['_modal'].initialize();
});

function fn_modal() {
	let $this = this;
	let PAGE_URL = "/cat/catProfile/catName/form";
	let TARGET_CD;
	
	this.$table;
	this.initialize = function() {
		$this.initData();
	}
	
	this.initData = function() {
		TARGET_CD = $("#target_cd").val()? Number($("#target_cd").val()) : null;
		$this.formManager.getData();
		
	}
	
	this.formManager = {
		getData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {
							"target_cd" : TARGET_CD
						}
				, success : function(data){
					$this.formManager.setData(data);
				}
			});
		},
		setData : function(data) {
			//form 정보
			$("#catName-form").bindJson(data);
		},
		validate : function(){
			if(!$("#catName-form").valid()){
				 return false;
			}
			return true;
		},
		save : function(callBackFunc) {
			if($this.formManager.validate()){
				let _data = this.getSaveData();
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, data : _data
					, success : function(data){
						alert("애칭이 설정 되었습니다.");
						callBackFunc();
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#catName-form").serializeObject();
			if(jsonData['target_cd']){
				jsonData['target_cd'] = Number(jsonData['target_cd']);
			}
			return jsonData;
		}
		
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>