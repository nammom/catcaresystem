<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<!--template 영역  -->
</div>
<script id="tmpl" type="text/x-jsrender">
<div class="col-md-12">
		<div class="row">
			<div id="image">
				<img class="img-fluid" src="/images/{{if file_path}}{{:file_path}}{{else}}cmn/basiccatprofile.jpg{{/if}}"/>
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
				<td colspan="2">{{:cat_cd}}</td>
			</tr>
			{{if cat_name}}
			<tr class="table-light">
				<th>나의 애칭</th>
				<td colspan="2">{{:cat_name}}</td>
			</tr>
			{{/if}}
			<tr class="table-light">
				<td colspan="3">{{>sido_cd}} {{>sigungu_cd}} {{>dong_cd}}</td>
			</tr>
			{{if care_reason_nm}}
			<tr class="table-light">
				<th>돌봄 종료 사유</th>
				<td>{{>care_reason_nm}}</td>
				<td>
					<button type="button" class="btn btn-primary btn-sm">상세보기</button>
				</td>
			</tr>
			{{/if}}
			<tr class="table-light">
				<th>품종</th>
				<td colspan="2">{{>cat_kind_nm}}</td>
			</tr>
			<tr class="table-light">
				<th>특징</th>
				<td></td>
				<td>
				    <button type="button" class="btn btn-primary btn-sm">상세보기</button>
				</td>
			</tr>			
			<tr class="table-light">
				<th>돌봄</th>
				<td colspan="2">
					<button type="button" id="btn-care" class="btn btn-warning ">♥</button>				
			    	<button type="button" id="btn-care-list" class="btn btn-warning">목록</button>
				</td>
			</tr>
			<tr class="table-light">
				<th>즐겨찾기</th>
				<td colspan="2">
					<button type="button" id="btn-bookmark" class="btn btn-warning">★</button>				
			    	<button type="button" id="btn-bookmark-list" class="btn btn-warning">목록</button>
				</td>
			</tr>
			{{if caution_yn == "Y"}}
			<tr class="table-light">
				<td colspan="2">주의가 필요한 고양이에요!</td>
			</tr>
			
			<tr class="table-light">
				<th>공격성</th>
				<td>{{>aggression}}</td>
				<td>
					<button type="button"  class="btn btn-primary btn-sm">상세보기</button>
				</td>					
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
					    <p>{{for disease}}{{>disease.disease_nm}} > {{>disease.disease_nm2}} > {{>disease.disease_nm3}}{{/for}}</p>
				  	</details>
				</td>
				<td>
					<button type="button"  class="btn btn-primary btn-sm">상세보기</button>
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
console.log("");

let _mypage = null;

$(document).ready(function() {

	_mypage = new fn_page();
	_mypage.initialize();
});

function fn_page() {
	let $this = this;
	let $template, $noInfoTmpl;
	
	this.initialize = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		
		$this.initData();
		/* $("#btn-save").click(function(){
			$this.formManager.save();
		}); */
		
		
	}
	
	this.initData = function() {
		let cat_cd="${cat_cd}";
		$this.dataManager.getData(cat_cd);
	}
	
	this.dataManager = {
		getData : function(cat_cd){
			$.ccs.ajax({
				url : "/cat/catProfile/selectData"
				, data : {"cat_cd" : Number(cat_cd)}
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
			$(".container")
				.empty()
				.append(htmlOut);
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>