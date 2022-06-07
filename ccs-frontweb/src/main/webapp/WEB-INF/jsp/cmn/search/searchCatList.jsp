<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<form id="search-form" class="form-group m-3">
		<c:if test="${groupFlag eq 'grp'}"><c:set var="group_yn" value="Y"/></c:if>
		<c:if test="${groupFlag eq 'cat'}"><c:set var="group_yn" value="N"/></c:if>
		<input type="hidden" id="groupFlag" name="groupFlag" value="<c:out value="${groupFlag}" />">
		<input type="hidden" id="group_yn" name="group_yn" value="<c:out value="${group_yn}" />">
		<div class="row">
			<div class="form-group col-md-1">
				<label for="sido" class="form-label">지역</label>
				
			</div>
			<div class="form-group col-md-3">
				<select id="sido" name="sido"  class="form-control" disabled>
                	<ccs:area code="sido" selectedValue="100" />
                </select>
			</div>
			<div class="form-group col-md-3">
				<select id="sigungu" name="sigungu"  class="form-control" disabled>
                	<ccs:area code="sigungu" selectedValue="110" />
                </select>
			</div>
			<div class="form-group col-md-3">
				<select id="dong" name="dong"  class="form-control">
                	<ccs:area code="dong" includeAll="전체"/> 
                </select> 
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-1">
	         	<label for="cat_name" class="form-label">애칭</label>
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
	        <div class="form-group col-md-2">
	         	<button class="btn btn-secondary my-2 my-sm-0 float-right" type="button" id="btn-search">검색</button>
	        </div>
	    </div>
	</form>	    
</div>
	<div class="col-md-12">
		<table id="careList"></table>
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
	let PAGE_URL = "/search/cat";
	let groupFlag;
	
	this.$table;
	this.initialize = function() {
		groupFlag = $("#groupFlag").val();
		$this.tableManager.createTable();
		
		$("#btn-search").click(function(){
			$this.tableManager.searchData();
		});
	}
	
	this.tableManager = {
		createTable : function() {
			let header = $("meta[name='_csrf_header']").attr('content');
			let token = $("meta[name='_csrf']").attr('content');
			
			let columnArr = [
								{
				    		 		"title": ""
				    		 		, "data": null
				    		 		, "name": ""
						 			, "orderable": false
				    		 		, "searchable": false
				    		 		, "render": function ( data, type, row, meta ) {
				    		 						return '<input type="checkbox" name="input-check"/>';
								            	}
				    		 	},
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
				    		 							return '<img class="img-profile-s" src="/images/cmn/basic_cat_profile.jpg"/>';
				    		 						}
								            	}
				    		 	},
						        {
				    		 		"title": "나의 애칭"
				    		 		, "data": "cat_name"
				    		 		, "name": "cat_name"
				    		 	},
						        {
				    		 		"title": "고유코드"
				    		 		, "data": "cat_cd"
				    		 		, "name": "cat_cd"
				    		 	}, 
						        {
				    		 		"title": "주소"
				    		 		, "data": "area_nm"
				    		 		, "name": "area_nm"
				    		 	},
						        {
				    		 		"title": "품종"
				    		 		, "data": "cat_kind_nm"
				    		 		, "name": "cat_kind_nm"
				    		 	}
				    		];
			if(groupFlag == "grp"){
				//그룹인 경우 품종 컬럼 제거
				let colIdx = $.ccs.findIndexByKey(columnArr, "name", "cat_kind_nm"); 
				columnArr.splice(colIdx,1);
			}
			$this.$table = $('#careList').DataTable({
				destroy : true,//테이블 파괴가능
				lengthChang : true,
				bLengthChange : true, // n개씩보기
				lengthMenu : [ [10, 25, 50, -1], [10, 25, 50, "All"] ], // * 실 페이지 설정  값 : 10/25/50/All 개씩보기
				ordering : true, //칼럼별 정렬

				processing : false,
			    serverSide : false, 
				bPaginate : false, //페이징처리
				filter : false,
				bFilter : false,
				searching : false, //검색기능 (*false로 두고 ajax로 구현하도록한다)
			    bAutoWidth : false, //자동너비
				ajax: {
					url : PAGE_URL + "/selectSearchCatList",
					type : "POST",
					data : function (d) {
						//d는 그리드 정보 객체, paging시 필요
						//검색정보
						let data = $("#search-form").serializeObject();
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
		},
		getRowData : function(obj) {
			return $this.$table.rows(obj).data().toArray();
		},
		getCheckedRows : function() {	//체크박스 체크된 행 가져오기
			let rowsArr = []; 
			$.each($("input[name='input-check']:checked"),function(i,e){
				rowsArr.push($(e).closest('tr').index());
			});
			return rowsArr;
		},
		getCheckedRowsData : function() {
			let arr = this.getCheckedRows();
			if(arr.length) {
				return this.getRowData(arr);
			}
			return null;
		},
		searchData : function() {	//검색
			return $this.$table.ajax.reload();
		}
		
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>