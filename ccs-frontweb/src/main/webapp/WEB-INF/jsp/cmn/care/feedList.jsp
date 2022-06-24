<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<input type="hidden" id="target_cd" value="<c:out value="${target_cd}" />"/>
<input type="hidden" id="target_type" value="<c:out value="${target_type}" />"/> 
<div class="col-md-12">
	<button type="button" id="btn-list" class="btn btn-warning">통계</button>
	<button type="button" id="btn-calendar" class="btn btn-warning">달력</button>
	<button type="button" id="btn-add" class="btn btn-warning">등록</button>
</div>
<div class="col-md-12 feedList-div">
<!--template 영역  -->
</div>
<script id="tmpl" type="text/x-jsrender">
	<div class="card bg-light mb-3">
    	<div class="card-header">{{>date}}</div>
    	<div class="card-body">
    		<h4 class="card-title">통계</h4>
      		<table class="table">
				<tr>
					<th></th>
					<th>합계</th>
					<th>주식</th>
					<th>간식</th>
					<th>기타</th>
					<th>물</th>
				</tr>
				<tr>
					<th>급여횟수</th>
					<td>{{>feedCountMap.all_count}}</td>
					<td>{{>feedCountMap.main_feed_count}}</td>
					<td>{{>feedCountMap.snack_count}}</td>
					<td>{{>feedCountMap.etc_count}}</td>
					<td>{{>feedCountMap.water_count}}</td>
				</tr>
				<tr>
					<th>급여여부</th>
					<td></td>
					<td>{{if feedCountMap.main_feed_count > 0}}O{{else}}X{{/if}}</td>
					<td>{{if feedCountMap.snack_count > 0}}O{{else}}X{{/if}}</td>
					<td>{{if feedCountMap.etc_count > 0}}O{{else}}X{{/if}}</td>
					<td>{{if feedCountMap.water_count > 0}}O{{else}}X{{/if}}</td>
				</tr>
			</table>
			<h4 class="card-title">급여 대상</h4>
			<table class="table">
				<tr>
					<th></th>
					<th>고유코드</th>
					<th>나의애칭</th>
					<th>급여시간</th>
				</tr>
				{{for feedList}}
				<tr class="feedList-tr" data-feedcd="{{>feed_cd}}">
					<td>
						<img class="img-profile-s" 
							src="/images/{{if file_path}}{{>file_path}}{{else}}cmn/{{if group_yn == "N"}}basic_cat_profile.jpg{{else}}basic_cat_grp_profile.jpg{{/if}}{{/if}}"/>
					</td>
					<td>{{>cat_cd}}</td>
					<td>{{>cat_name}}</td>
					<td>{{>feed_time1 + "시 " + feed_time2 + "분"}}</td>
				</tr>
				{{/for}}
			</table>
          </div>
       </div>
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
	let PAGE_URL = "/care/feed";
	let $template, $noInfoTmpl;
	let TARGET_CD, TARGET_TYPE;
	
	this.initialize = function() {
		
		$this.initData();
		
		$(document).on("click", ".feedList-tr", function(){
			let feed_cd = $(this).data("feedcd");
			$this.locationManager.detail(feed_cd);
		});
		
		$("#btn-list").click(function(){
			$this.locationManager.list();
		});
		
		$("#btn-calendar").click(function(){
			$this.locationManager.calendar();
		});
		
		$("#btn-add").click(function(){
			$this.locationManager.form();
		});
	}
	
	this.initData = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		TARGET_CD = $("#target_cd").val() ? Number($("#target_cd").val()) : null;
		TARGET_TYPE = $("#target_type").val();
		
		window['sessionStorage'].clear();
		window['sessionStorage'].setItem("target_type", TARGET_TYPE);
		window['sessionStorage'].setItem("target_cd", TARGET_CD);
		
		$this.dataManager.getData();
	}
	
	this.dataManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : this.getSelectData()
				, success : function(data){
					$this.dataManager.setData(data['feedList']);
				}
			});
		},
		//getData의 ajax Data
		getSelectData : function(){
			let data;
			switch(TARGET_TYPE){
				case "cat" : data = {"cat_cd" : TARGET_CD, "feed_date" : "202206"};
					break;
				case "grp" : data = {"cat_grp_cd" : TARGET_CD, "feed_date" : "202206"};
					break;
				case "habitat" : data = {"habitat_cd" : TARGET_CD, "feed_date" : "202206"};
					break;
			}
			return data;
		},
		setData : function(list){
			let htmlOut;
			if(list && list.length){
				let htmlOut = $template.render(list);
				$(".feedList-div")
				.empty()
				.append(htmlOut);
			}else{
				htmlOut = $noInfoTmpl.render();
				$(".container")
				.empty()
				.append(htmlOut);
			}
		}
	}
	
	this.locationManager = {
		detail : function(feed_cd){
			location.href = PAGE_URL + "/detail/" + feed_cd;
		},
		form : function(feed_cd){
			location.href = PAGE_URL + "/form/" + TARGET_CD;
		},
		list : function(feed_cd){
			location.href = PAGE_URL + "/" + TARGET_TYPE + "/" + TARGET_CD;
		},
		calendar : function(feed_cd){
			location.href = PAGE_URL + "/calendar/" + TARGET_TYPE + "/" + TARGET_CD;
		},
	}
	
	this.actionManager = {
			care : function(){
				$.ccs.ajax({
					url : PAGE_URL + "/care"
					, data : {"target_cd" : Number(CAT_CD)
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
					, data : {"target_cd" : Number(CAT_CD)
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