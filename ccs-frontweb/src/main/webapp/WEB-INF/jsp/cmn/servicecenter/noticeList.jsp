<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12 noticeList-div">
<!--template 영역  -->
</div>
<div class="pagination"></div>
<script id="tmpl" type="text/x-jsrender">
	<div class="card bg-light mb-3">
    	<div class="accordion-header card-header"> 
			{{>noti_sj}}
		</div>
		<div class="accordion-body">
    		<div class="card-body">
				<div>
					{{>noti_cn}}
				</div>
			</div>
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
	let PAGE_URL = "/servicecenter/notice";
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
					$this.dataManager.setData(data['noticeList']);
				}
			});
		},
		setData : function(list){
			let htmlOut;
			
			if(list && list.length){
				htmlOut = $template.render(list);
				$(".noticeList-div").append(htmlOut);
			}else{
				htmlOut = $noInfoTmpl.render();
				if($(".noticeList-div").children().length == 0){
					$(".noticeList-div").append(htmlOut);
				}else{
					this.paging.endPage = true;
				}
			}
		}
	}

	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>