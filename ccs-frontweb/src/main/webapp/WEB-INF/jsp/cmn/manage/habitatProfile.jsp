<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<div class="container">
	<form>
		<input type="hidden" id="habitat_cd" name="habitat_cd" value="<c:out value="${habitat_cd}" />"/>
	</form>
	<div class="profile">
		<!--template 영역  -->
	</div>
</div>


<script id="tmpl" type="text/x-jsrender">
<div class="col-md-12">
		<div class="row">
			<div id="image">
				<img class="img-fluid" 
					src="/images/{{if file_path}}{{>file_path}}{{else}}cmn/basic_habitat_profile.jpg{{/if}}"/>
			</div>
		</div>
		<table class="table table-hover">
			<colgroup>
				<col width = "30%">
				<col width = "50%">
				<col width = "20%">
			</colgroup>
			<tr class="table-light">
				<th>서식지 코드</th>
				<td>{{>habitat_cd}} {{>_SESSION_USER_CD_}} {{>user_cd}}</td>
				<td>
					{{if _SESSION_USER_CD_ == user_cd}}
						<button type="button" id="btn-edit" class="btn btn-primary btn-sm">수정</button>
					{{/if}}
				</td>
			</tr>
			<tr class="table-light">
				<th>서식지 명</th>
				<td colspan="2">{{>habitat_nm}}</td>
			</tr>
			<tr class="table-light">
				<td colspan="3">{{>area_nm}} {{>address}}</td>
			</tr>
		</table>
	</div>
</script>
<script id="noInfoTmpl" type="text/x-jsrender">
	<div class="col-md-12">
		<div class="row">
			<h3>존재하지 않는 정보입니다.</h3>
		</div>
	</div>
</script>
<script>
let _mypage = null;

$(document).ready(function() {
	_mypage = new fn_page();
	_mypage.initialize();
});

function fn_page() {
	let $this = this;
	let PAGE_URL = "/manage/habitat/profile";
	let $template, $noInfoTmpl;
	let HABITAT_CD;
	
	this.initialize = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		HABITAT_CD = Number($("#habitat_cd").val());

		$this.initData();

		$(document).on("click", "#btn-edit", function(){
			$this.locationManager.edit();
		});
	}
	
	this.initData = function() {
		$this.dataManager.getData();
	}
	
	this.dataManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {"habitat_cd" : HABITAT_CD}
				, success : function(data){
					$this.dataManager.setData(data);
				}
			});
		},
		setData : function(list){
			let htmlOut;
			if(list){
				htmlOut = $template.render(list);
			}else{
				htmlOut = $noInfoTmpl.render();
			}
			$(".profile")
				.empty()
				.append(htmlOut);
		}
	}

	
	this.locationManager = {
		edit : function() {
			location.href = "/manage/habitat/form/" + HABITAT_CD;				
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>