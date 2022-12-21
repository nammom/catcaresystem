<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<style>
	.table td {
		vertical-align: middle;
	}
</style>
<div class="container">
	<form onsubmit="return false">
		<input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
	</form>
	<div class="profile">
		<!--template 영역  -->
	</div>
</div>


<script id="tmpl" type="text/x-jsrender">
<div class="col-md-12">

		<table class="table table-hover">
			<colgroup>
				<col width = "30%">
				<col width = "50%">
				<col width = "20%">
			</colgroup>
			<tr class="table-light">
				<td><img class="img-profile-s" 
					src="/images/{{if file_path}}{{>file_path}}{{else}}cmn/basic_user_profile.jpg{{/if}}"/>
				</td>
				<td>{{>nickname}}</td>
				<td>
					<button type="button" id="btn-edit" class="btn btn-primary btn-sm">수정</button>
				</td>
			</tr>
			<tr class="table-light">
				<tr></th>
				<td colspan="2">
					<details open>
					    <summary></summary>
						<div class="bs-component">
							{{>introduction}}
						</div>
				  	</details>
				</td>	
			</tr>
			<tr class="table-light">
				<th>돌봄</th>
				<td colspan="2">
			    	<button type="button" id="btn-care-list" class="btn btn-warning">목록</button>
				</td>
			</tr>
			<tr class="table-light">
				<th>즐겨찾기</th>
				<td colspan="2">
			    	<button type="button" id="btn-bookmark-list" class="btn btn-warning">목록</button>
				</td>
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
	let PAGE_URL = "/user/profile";
	let $template, $noInfoTmpl;
	let USER_CD;
	
	this.initialize = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		USER_CD = Number($("#user_cd").val());

		$this.initData();

		$(document).on("click", "#btn-edit", function(){
			//프로필 수정
			$this.locationManager.edit();
		});
		$(document).on("click", "#btn-care-list", function(){
			$this.locationManager.careList();
		});
		$(document).on("click", "#btn-bookmark-list", function(){
			$this.locationManager.bookMarkList();
		});
	}
	
	this.initData = function() {
		$this.dataManager.getData();
	}
	
	this.dataManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {"user_cd" : USER_CD}
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
			location.href = PAGE_URL + "/form/" + USER_CD;				
		},
		careList : function(){
			location.href =  "/user/care/" + USER_CD;
		},
		bookMarkList : function(){
			location.href = "/user/bookMark/" + USER_CD;
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>