<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<style>
.answer_div {
    background-color: rgba(0, 0, 0, 0.03);
}
</style>
<body>
<div class="container">
<div class="col-md-12">
	<button type="button" id="btn-add" class="btn btn-warning">등록</button>
</div>
<div class="col-md-12 questionList-div">
<!--template 영역  -->
</div>
<div class="pagination"></div>
<script id="tmpl" type="text/x-jsrender">
	<div class="card bg-light mb-3">
    	<div class="accordion-header card-header"> 
			제목 : {{>title}} <br/>
			답변 상태 : {{if answer_yn == "Y"}}답변{{else}}미답변{{/if}}
		</div>
		<div class="accordion-body">
    	<div class="card-body">
			<div>
				{{>content}}
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
	{{if answer_yn == "Y"}}
	   <div class="answer_div card-body">
		답변 일자 : {{>answer_dt}} <br/>
	   	답변 : {{>comment}}
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
	let PAGE_URL = "/servicecenter/question";
	let $template, $noInfoTmpl;
	let start = 1;
	let length = 10;
	
	this.initialize = function() {
		
		$this.initData();
		
		$("#btn-add").click(function(){
			$this.locationManager.form();
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
/* 	    
		$(document).on("click", ".accordion-header", function(){
	    	let $accorrdionBody = $(this).next();
			if(!$accorrdionBody.hasClass("accordion-body")) {
				return;
			}
			if($accorrdionBody.hasClass("show")) {
				$accorrdionBody.removeClass("show");
			} else {
				$accorrdionBody.addClass("show");
			}
		}); */
	}
	
	this.initData = function() {
		$template = $("#tmpl");
		$noInfoTmpl = $("#noInfoTmpl");
		
		//데이터 조회
		$this.dataManager.getData();
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
				, data : {"paging" : this.paging}
				, success : function(data){
					$this.dataManager.setData(data['questionList']);
				}
			});
		},
		setData : function(list){
			let htmlOut;
			
			if(list && list.length){
				htmlOut = $template.render(list);
				$(".questionList-div").append(htmlOut);
			}else{
				htmlOut = $noInfoTmpl.render();
				if($(".questionList-div").children().length == 0){
					$(".questionList-div").append(htmlOut);
				}else{
					this.paging.endPage = true;
				}
			}
			//이미지 슬라이드 생성
			$('.carousel').carousel();
		}
	}
	
	this.locationManager = {
		form : function(){
			location.href = PAGE_URL + "/form";
		},
		list : function(){
			location.href = PAGE_URL;
		}
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>