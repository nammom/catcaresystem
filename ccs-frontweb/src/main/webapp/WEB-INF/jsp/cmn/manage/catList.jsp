<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<button type="button" id="btn-add" class="btn btn-warning">등록</button>
</div>
<div class="col-md-12">
	<form onsubmit="return false" id="search-form" class="form-group m-3">
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
	         	<label for="cat_name" class="form-label">고양이애칭</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="cat_name" name="cat_name" type="text">
	        </div>
	        <div class="form-group col-md-3">
	         	<label for="cat_cd" class="form-label">고유코드</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="cat_cd" name="cat_cd" type="text">
	        </div>
	    </div>
	    <div class="row">
	    	<div class="form-group col-md-2">
	         	<label for="group_yn" class="form-label">무리 여부</label>
        	</div>
	    	<div class="form-group col-md-3">
				<select id="group_yn" name="group_yn" class="form-control">
                	<option value="" >전체</option>
                	<option value="N" >개별</option>
                	<option value="Y" >무리</option>
                </select> 
			</div>
	    	<div class="form-group col-md-3">
	         	<label for="habitat_cd" class="form-label">고양이 서식지 코드</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="habitat_cd" name="habitat_cd" type="text">
	        </div>
	        <div class="form-group col-md-1">
	         	<button class="btn btn-secondary my-2 my-sm-0 float-right" type="button" id="btn-search">검색</button>
	        </div>
	    </div>
	</form>	    
</div>
	<div class="col-md-12">
		<table id="catList"></table>
	</div>
</div>

<script>
let _mypage = null;

$(document).ready(function() {
	_mypage = new fn_page();
	_mypage.initialize();
});

function fn_page() {
	let $this = this;
	let PAGE_URL = "/manage/cat";
	
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
				    		 							if(row['group_yn'] == 'Y') {
				    		 								return '<img class="img-profile-s" src="/images/cmn/basic_cat_grp_profile.jpg"/>';
				    		 							} else {
					    		 							return '<img class="img-profile-s" src="/images/cmn/basic_cat_profile.jpg"/>';
				    		 							}
				    		 						}
								            	}
				    		 	},
						        {
				    		 		"title": "고유코드"
				    		 		, "data": "cat_cd"
				    		 		, "name": "cat_cd"
				    		 	},
				    		 	{
				    		 		"title": "애칭"
				    		 		, "data": "cat_name"
				    		 		, "name": "cat_name"
				    		 	},
						        {
				    		 		"title": "품종"
				    		 		, "data": "cat_kind_nm"
				    		 		, "name": "cat_kind_nm"
				    		 	},
						        {
				    		 		"title": "주소"
				    		 		, "data": "area_nm"
				    		 		, "name": "area_nm"
				    		 	}
				    		 ];
				        	
        	let colIdx = $.ccs.findIndexByKey(columnArr, "name", "cat_cd"); 
        		 	
			$this.$table = $('#catList').DataTable({
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
			        if(row[0]['cat_cd']){
						$this.locationManager.detail(row[0]['cat_cd'], row[0]['group_yn']);
			        }
			    }
			} );
		},
		searchData : function() {	//검색
			return $this.$table.ajax.reload();
		}
		
	}
	
	this.locationManager = {
		detail : function(_cat_cd, _group_yn) {
			if(_group_yn == "Y") {
				location.href = "/cat/catProfile/grp/" + _cat_cd;				
			}else {
				location.href = "/cat/catProfile/cat/" + _cat_cd;	
			}
		},
		form : function(){
			location.href = PAGE_URL + "/form";
		}
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>