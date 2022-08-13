<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="cat_cd" name="cat_cd" value="<c:out value="${cat_cd}" />"/>
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
					src="/images/{{if file_path}}{{>file_path}}{{else}}cmn/{{if group_yn == "N"}}basic_cat_profile.jpg{{else}}basic_cat_grp_profile.jpg{{/if}}{{/if}}"/>
			</div>
		</div>
		<table class="table table-hover">
			<colgroup>
				<col width = "30%">
				<col width = "50%">
				<col width = "20%">
			</colgroup>
			<tr class="table-light">
				<th>고양이 코드</th>
				<td colspan="2">{{>cat_cd}}</td>
			</tr>
			{{if cat_name}}
			<tr class="table-light">
				<th>나의 애칭</th>
				<td colspan="2">{{>cat_name}}</td>
			</tr>
			{{/if}}
			<tr class="table-light">
				<td colspan="3">{{>sido_nm}} {{>sigungu_nm}} {{>dong_nm}}</td>
			</tr>
			{{if care_type_nm}}
			<tr class="table-light">
				<th>돌봄 상태</th>
				<td>{{>care_type_nm}} : {{>care_reason_nm}}</td>
				<td>
					<button type="button" id="btn-care-detail" class="btn btn-primary btn-sm">상세보기</button>
				</td>
			</tr>
			{{/if}}
			<tr class="table-light">
				<th>소개</th>
				<td colspan="2">
					<details open>
					    <summary></summary>
						<div class="bs-component">
							{{>introduction}}
						</div>
				  	</details>
				</td>			
			</tr>
			{{if group_yn == "N"}}
			<tr class="table-light">
				<th>품종</th>
				<td colspan="2">{{>cat_kind_nm}}</td>
			</tr>
			<tr class="table-light">
				<th>추정나이</th>
				<td colspan="2">{{>age}}</td>
			</tr>
			<tr class="table-light">
				<th>성별</th>
				<td colspan="2">{{>gender}}</td>
			</tr>
			<tr class="table-light">
				<th>중성화 여부</th>
				<td colspan="2">{{>neutering_yn}}</td>
			</tr>
			{{/if}}		
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
			<tr class="table-light">
				<th>특징</th>
				<td></td>
				<td>
				    <button type="button" id="btn-character-detail" class="btn btn-primary btn-sm">상세보기</button>
				</td>
			</tr>	
			{{if caution_yn == "Y"}}
			<tr class="table-light">
				<td colspan="3">
					<p class="text-danger font-weight-bold">주의가 필요한 고양이에요!</p>
				</td>
			</tr>
			<tr class="table-light">
				<th>공격성</th>
				<td>{{>aggression}}</td>
				<td></td>					
			</tr>
			<tr class="table-light">
				<th>예민함</th>
				<td>{{>sensitivity}}</td>
				<td></td>				
			</tr>
			{{/if}}
			{{if disease && disease.length}}
			<tr class="table-light">
				<th>질병</th>
				<td>
					<details>
					    <summary></summary>
						<div class="bs-component">
              				<ul class="list-group">
							{{for disease}}
                				<li class="list-group-item d-flex justify-content-between align-items-center">
                  					{{>disease_nm}}
									{{if disease_nm2}}> {{>disease_nm2}}{{/if}}
									{{if disease_nm3}}> {{>disease_nm3}}{{/if}}
                				</li>
							{{/for}}
              				</ul>
						</div>
				  	</details>
				</td>
				<td>
					<button type="button" id="btn-health-detail" class="btn btn-primary btn-sm">상세보기</button>
				</td>				
			</tr>
			{{/if}}												
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
	let PAGE_URL = "/cat/catProfile";
	let $template, $noInfoTmpl;
	let CAT_CD;
	
	this.initialize = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		CAT_CD = Number($("#cat_cd").val());
		$this.initData();
		
		$(document).on("click", "#btn-care-detail", function(){
			$this.locationManager.careInfo();
		});
		$(document).on("click", "#btn-character-detail", function(){
			$this.locationManager.character();
		});
		$(document).on("click", "#btn-health-detail", function(){
			$this.locationManager.health();
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
				, data : {"cat_cd" : CAT_CD}
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
			careList : function(){
				location.href = PAGE_URL + "/careList/" + CAT_CD;
			},
			bookMarkList : function(){
				location.href = PAGE_URL + "/bookMarkList/" + CAT_CD;
			},
			careInfo : function(page){
				location.href = "/cat/careInfo" + "/" + CAT_CD;
			},
			character : function(page){
				location.href = "/care/character/cat/" + CAT_CD;
			},
			health : function(page){
				location.href = "/care/health/cat/" + CAT_CD;
			}
			
	}
	
	this.actionManager = {
			care : function(){
				$.ccs.ajax({
					url : PAGE_URL + "/care"
					, data : {"target_cd" : CAT_CD
							, "target_type" : 1}
					, success : function(data){
						//$this.actionManager.careCallback(data);
						$this.initData();
					}
				});
			},
			bookMark : function(){
				$.ccs.ajax({
					url : PAGE_URL + "/bookMark"
					, data : {"target_cd" : CAT_CD
							, "target_type" : 1}
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
			}
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>