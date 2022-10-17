<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<%@ include file="/WEB-INF/jsp/cmn/menu/manageNav.jsp"%>
<div class="container">
	<form>
		<input type="hidden" id="target_cd" name="target_cd" value="<c:out value="${habitat_cd}" />"/>
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
			<tr class="table-light">
				<th>돌봄</th>
				<td colspan="2">
					<button type="button" id="btn-care" class="btn btn-warning">{{if care_yn == 0}}♡{{else}}♥{{/if}}</button>				
			    	<button type="button" id="btn-care-list" class="btn btn-warning">목록</button>
				</td>
			</tr>
			<tr class="table-light">
				<th>즐겨찾기</th>
				<td colspan="2">
					<button type="button" id="btn-bookmark" class="btn btn-warning">{{if bookmark_yn == 0}}☆{{else}}★{{/if}}</button>				
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
	let PAGE_URL = "/manage/habitat/profile";
	let $template, $noInfoTmpl;
	let HABITAT_CD;
	
	this.initialize = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		HABITAT_CD = Number($("#target_cd").val());

		$this.initData();

		$(document).on("click", "#btn-edit", function(){
			$this.locationManager.edit();
		});
		$(document).on("click", "#btn-care", function(){
			//돌봄
			$this.actionManager.care();
		});
		$(document).on("click", "#btn-care-list", function(){
			$this.locationManager.careList();
		});
		$(document).on("click", "#btn-bookmark", function(){
			//즐겨찾기
			$this.actionManager.bookMark();			
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

	this.actionManager = {
			care : function(){
				$.ccs.ajax({
					url : "/care/care/save"
					, data : $this.actionManager.getSaveData()
					, success : function(data){
						//$this.actionManager.careCallback(data);
						$this.initData();
					}
				});
			},
			bookMark : function(){
				$.ccs.ajax({
					url : "/care/bookMark/save"
					, data : $this.actionManager.getSaveData()
					, success : function(data){
						//$this.actionManager.careCallback(data);
						$this.initData();
					}
				});
			},
			careCallback : function(data){
				if(data.status == "Y"){
					$("btn-care").html("♥");
				}else{
					$("btn-care").html("♡");
				}
			},
			bookmarkCallback : function(data){
				if(data.status == "Y"){
					$("btn-care").html("★");
				}else{
					$("btn-care").html("☆");
				}
			},
			getSaveData : function(){
				return {"target_cd" : HABITAT_CD, "target_type" : "habitat"};
				
			}
	}
	
	this.locationManager = {
		edit : function() {
			location.href = "/manage/habitat/form/" + HABITAT_CD;				
		},
		careList : function(){
			location.href = "/care/care/habitat/" + HABITAT_CD;
		},
		bookMarkList : function(){
			location.href = "/care/bookMark/habitat/" + HABITAT_CD;
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>