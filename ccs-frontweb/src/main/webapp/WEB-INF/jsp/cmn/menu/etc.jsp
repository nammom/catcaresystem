<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<div class="container">
	<ul class="etc-nav list-group"></ul>
</div>


<script id="tmpl" type="text/x-jsrender">
<a href="{{>menu_link}}"><li class="etc-nav list-group-item">{{>menu_nm}}</li></a>
</script>

<script>
let _mypage = null;

$(document).ready(function() {
	_mypage = new fn_page();
	_mypage.initialize();
});

function fn_page() {
	let $this = this;
	let PAGE_URL = "/menu/etc";
	let $template
	let cat_cd;
	
	this.initialize = function() {
		$template = $("#tmpl");
		
		$this.initData();

	}
	
	this.initData = function() {
		$this.dataManager.getData();
	}
	
	this.dataManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {menuCount : $.ccs.menu.menuCount}
				, success : function(data){
					$this.dataManager.setData(data);
				}
			});
		},
		setData : function(data){
			if(data['etcMenuList']){
				let htmlOut = $template.render(data['etcMenuList']);
				$(".etc-nav")
				.empty()
				.append(htmlOut);
			}
			
		}
	}
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>