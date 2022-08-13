<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<button type="button" id="btn-add" class="btn btn-warning">등록</button>
</div>
<div class="col-md-12">
	<form id="search-form" class="form-group m-3">
		<input type="hidden" id="target_cd" name="cat_cd" value="<c:out value="${cat_cd}" />">
		<div class="row">
			<div class="form-group col-md-2">
				<label for="sido" class="form-label">지역</label>
			</div>
			<div class="form-group col-md-3">
				<select id="sido_cd" name="sido_cd" data-code="sido" class="form-control area-select">
                	<ccs:area code="sido" includeAll="전체"/>
                </select>
			</div>
			<div class="form-group col-md-3">
				<select id="sigungu_cd" name="sigungu_cd" data-code="sigungu" class="form-control area-select">
                	<option value="">전체</option>
                </select>
			</div>
			<div class="form-group col-md-3">
				<select id="dong_cd" name="dong_cd" data-code="dong" class="form-control area-select">
                	<option value="">전체</option>
                </select> 
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-2">
	         	<label for="habitat_nm" class="form-label">서식지명</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="habitat_nm" name="habitat_nm" type="text">
	        </div>
	        <div class="form-group col-md-3">
	         	<label for="habitat_cd" class="form-label">고유코드</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="habitat_cd" name="habitat_cd" type="text">
	        </div>
	    </div>
	    <div class="row">
	    	<div class="form-group col-md-2">
	         	<label for="cat_cd" class="form-label">서식고양이코드</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="cat_cd" name="cat_cd" type="text">
	        </div>
	        <div class="form-group col-md-6">
	         	<button class="btn btn-secondary my-2 my-sm-0 float-right" type="button" id="btn-search">검색</button>
	        </div>
	    </div>
	</form>	    
</div>
	<div class="col-md-12">
		<table id="habitatList"></table>
	</div>
</div>

<script>
$(document).ready(function() {
	if(window['_modal']) window['_modal'] = null;
	window['_modal'] = new fn_modal();
	window['_modal'].initialize();
});

function fn_modal() {
	let $this = this;
	let PAGE_URL = "/manage/habitat";
	let CAT_CD;
	
	this.$table;
	this.initialize = function() {
		$this.initData();
		
		$("#btn-search").click(function(){
			$this.tableManager.searchData();
		});
		
		$("#btn-add").click(function(){
			$this.locationManager.form();
		});
	}
	
	this.initData = function() {
		
		$this.tableManager.createTable();
		
	}
	
	this.tableManager = {
		createTable : function() {
			let header = $("meta[name='_csrf_header']").attr('content');
			let token = $("meta[name='_csrf']").attr('content');
			
			let columnArr = [
								{
							 		"title": "프로필"
				    		 		, "data": "file_path"
				    		 		, "name": "file_path"
						 			, "orderable": false
				    		 		, "searchable": false
				    		 		, "render": function ( data, type, row, meta ) {
				    		 						if(data){
							        		 	      	return '<img class="img-profile-s" src="/images/' + data + '"/>';
				    		 						}else{
				    		 							return '<img class="img-profile-s" src="/images/cmn/basic_habitat_profile.jpg"/>';
				    		 						}
								            	}
				    		 	},
						        {
				    		 		"title": "고유코드"
				    		 		, "data": "habitat_cd"
				    		 		, "name": "habitat_cd"
				    		 	},
				    		 	{
				    		 		"title": "서식지명"
				    		 		, "data": "habitat_nm"
				    		 		, "name": "habitat_nm"
				    		 	},
						        {
				    		 		"title": "주소"
				    		 		, "data": "area_nm"
				    		 		, "name": "area_nm"
				    		 	},
						        {
				    		 		"title": "상세주소"
				    		 		, "data": "address"
				    		 		, "name": "address"
				    		 	}
				    		 ];
				        	
        	let colIdx = $.ccs.findIndexByKey(columnArr, "name", "habitat_cd"); 
        		 	
			$this.$table = $('#habitatList').DataTable({
				order : [[colIdx, 'desc']],// 최초 로딩시 정렬 컬럼 설정
				destroy : true,//테이블 파괴가능
			    processing : true,
			    serverSide : true, 
				bPaginate : true, //페이징처리
				lengthChang : true,
				bLengthChange : true, // n개씩보기
				lengthMenu : [ [10, 25, 50, -1], [10, 25, 50, "All"] ], // * 실 페이지 설정  값 : 10/25/50/All 개씩보기
				ordering : true, //칼럼별 정렬
				select : true,
				filter : false,
				bFilter : false,
				searching : false, //검색기능 (*false로 두고 ajax로 구현하도록한다)
			    bAutoWidth : false, //자동너비
				ajax: {
					url : PAGE_URL + "/selectData",
					type : "POST",
					data : function (d) {
						//d는 그리드 정보 객체, paging시 필요
						//검색정보
						let data = $("#search-form").serializeObject();
						if(data['habitat_cd']) data['habitat_cd'] = Number(data['habitat_cd']);
						if(data['cat_cd']) data['cat_cd'] = Number(data['cat_cd']);
						d['data'] = data;
						
						//그리드 정보 + 검색정보							
		                return JSON.stringify(d);
		            },
					dataType : "JSON",
					contentType : "application/json; charset=utf-8",
					beforeSend : function(xhr){
				        xhr.setRequestHeader(header, token);
				    }
				},
		        columns : columnArr 
		    });
			
			$this.$table.on( 'select', function ( e, dt, type, indexes ) {
			    if ( type === 'row' ) {
			        let row = $.ccs.table.getSelectedRows($this.$table);
			        if(row[0]['habitat_cd']){
						$this.locationManager.detail(row[0]['habitat_cd']);
			        }
			    }
			} );
		},
		searchData : function() {	//검색
			return $this.$table.ajax.reload();
		}
		
	}
	
	this.locationManager = {
		detail : function(_habitat_cd) {
			location.href = PAGE_URL + "/profile/" + _habitat_cd;				
		},
		form : function(){
			location.href = PAGE_URL + "/form";
		}
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>