<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<button type="button" id="btn-add" class="btn btn-warning">등록</button>
</div>
<div class="col-md-12">
	<form id="search-form" class="form-group m-3">
		<input type="hidden" id="target_cd" value="<c:out value="${target_cd}" />"/>
		<input type="hidden" id="target_type" value="<c:out value="${target_type}" />"/> 
	</form>	    
</div>
<div class="col-md-12 healthList-div">
<!--template 영역  -->
</div>
<div class="pagination"></div>

<script id="tmpl" type="text/x-jsrender">
	<div class="card bg-light mb-3">
    	<div class="card-header">
			<img class="img-profile-s" 
				src="/images/{{if file_path}}{{>file_path}}{{else}}cmn/{{if group_yn == "N"}}basic_cat_profile.jpg{{else}}basic_cat_grp_profile.jpg{{/if}}{{/if}}"/>
			고양이 코드 : {{>cat_cd}} {{if cat_name}}({{>cat_name}}){{/if}} 
			작성자 : {{>nickname}}
			작성일 : {{>reg_dt}}
			{{if user_cd == _SESSION_USER_CD_}}
				<button type="button" id="btn-edit" class="btn btn-warning" data-healthcd="{{>health_cd}}">수정</button>
			{{/if}}
		</div>
    	<div class="card-body">
    		{{if disease_nm}}{{>disease_nm}}{{/if}}
			{{if disease_nm2}}> {{>disease_nm2}}{{/if}}
			{{if disease_nm3}}> {{>disease_nm3}}{{/if}}
			<div>
				{{>disease_detail}}
			</div>
			{{if fileList}}
			<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" data-touch="false" data-interval="false">
  				<div class="carousel-indicators">
				{{if fileList.length > 1}}
				{{for fileList}}
    				<button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="{{:#index}}" {{if #index == 0}}class="active"{{/if}} aria-current="true" aria-label="Slide {{:#index}}"></button>
				{{/for}}
				{{/if}}
  				</div>
  			<div class="carousel-inner">
			{{for fileList}}
    			<div class="carousel-item{{if #index == 0}} active{{/if}}">
      				<img src="/images/{{>file_path}}" class="d-block w-100" alt="{{>file_nm}}">
    			</div>
			{{/for}}
  			</div>
			{{if fileList.length > 1}}
  				<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
    				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
    				<span class="visually-hidden">Previous</span>
  				</button>
  				<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
    				<span class="carousel-control-next-icon" aria-hidden="true"></span>
    				<span class="visually-hidden">Next</span>
  				</button>
			{{/if}}
			</div>
			{{/if}}
       </div>
	</div>
</script>
<script id="noInfoTmpl" type="text/x-jsrender">
	<div class="col-md-12">
		<div class="row">
			<h3>정보가 없습니다.</h3>
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
	let PAGE_URL = "/care/health";
	let $template, $noInfoTmpl;
	let TARGET_CD, TARGET_TYPE;
	let start = 1;
	let length = 10;
	
	this.initialize = function() {
		
		$this.initData();
		
		$("#btn-add").click(function(){
			$this.locationManager.form();
		});
		
		$(document).on("click", "#btn-edit", function(){
			let healthCd = $(this).data("healthcd");
			$this.locationManager.edit(healthCd);
		});
	    
	    window.onscroll = function(e) {
	      if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) { 
	        setTimeout(function(){
	        	if(!$this.dataManager.paging.endPage){
		          	$this.dataManager.paging.start++;
		        	//데이터 조회
		  			$this.dataManager.getData();
	        	}
	        }, 1000)  
	      }
	    }
	    
	    $(document).on("click", ".carousel-control-prev", function(){
	    	$(this.parentElement).carousel('prev');
		});
	    
	    $(document).on("click", ".carousel-control-next", function(){
	    	$(this.parentElement).carousel('next');
		});
	}
	
	this.initData = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		TARGET_CD = $("#target_cd").val() ? Number($("#target_cd").val()) : null;
		TARGET_TYPE = $("#target_type").val();
		
		//데이터 조회
		$this.dataManager.getData();
		
		//뒤로가기 시 데이터 유지용 아이템 셋팅
		window['sessionStorage'].clear();
		window['sessionStorage'].setItem("target_type", TARGET_TYPE);
		window['sessionStorage'].setItem("target_cd", TARGET_CD);
	}
	
	this.dataManager = {
		paging : {
			start : 1
			, length : 10
			, endPage : false
		},
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : this.getSelectData()
				, success : function(data){
					$this.dataManager.setData(data['healthList']);
				}
			});
		},
		//getData의 ajax Data
		getSelectData : function(){
			let data;
			switch(TARGET_TYPE){
				case "cat" : data = {"cat_cd" : TARGET_CD, "paging" : this.paging};
					break;
				case "grp" : data = {"cat_grp_cd" : TARGET_CD, "paging" : this.paging};
					break;
				case "habitat" : data = {"habitat_cd" : TARGET_CD, "paging" : this.paging};
					break;
			}
			return data;
		},
		setData : function(list){
			let htmlOut;
			
			if(list && list.length){
				htmlOut = $template.render(list);
				$(".healthList-div").append(htmlOut);
			}else{
				htmlOut = $noInfoTmpl.render();
				if($(".healthList-div").children().length == 0){
					$(".healthList-div").append(htmlOut);
				}else{
					this.paging.endPage = true;
				}
			}
			//이미지 슬라이드 생성
			$('.carousel').carousel();
		}
	}
	
	this.locationManager = {
		edit : function(health_cd){
			location.href = PAGE_URL + "/detail/" + health_cd;
		},
		form : function(){
			location.href = PAGE_URL + "/form/" + TARGET_CD;
		},
		list : function(){
			location.href = PAGE_URL + "/" + TARGET_TYPE + "/" + TARGET_CD;
		}
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>