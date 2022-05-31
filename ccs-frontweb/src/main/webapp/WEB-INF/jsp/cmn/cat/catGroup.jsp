<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<form id="search-form" class="form-group m-3">
		<input type="hidden" id="cat_cd" name="cat_cd" value="<c:out value="${cat_cd}" />">
	</form>	    
</div>
	<div class="col-md-12">
		<ul class="nav nav-tabs">
		<c:if test="${groupFlag eq 'grp'}">
          <li class="nav-item">
            <a class="nav-link active" data-bs-toggle="tab" href="#catGroup">무리</a>
          </li>
		</c:if>
		<c:if test="${groupFlag eq 'cat'}">
          <li class="nav-item">
            <a class="nav-link active" data-bs-toggle="tab" href="#cat">개별</a>
          </li>
        </c:if>
        </ul>
        <div id="myTabContent" class="tab-content">
        <c:if test="${groupFlag eq 'grp'}">
          <div class="tab-pane fade active show" id="catGroup">
          	<div class="col-md-12">
				<table id="catGroupList"></table>
			</div>
          </div>
        </c:if>
        <c:if test="${groupFlag eq 'cat'}">
          <div class="tab-pane fade active show" id="cat">
          	<div class="col-md-12">
				<table id="catList"></table>
			</div>
          </div>
        </c:if>
        </div>
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
	let PAGE_URL = "/cat/catGroup";
	let cat_cd, groupFlag;
	
	this.$table;
	this.initialize = function() {
		cat_cd = "<c:out value="${cat_cd}" />";
		groupFlag = "<c:out value="${groupFlag}" />";
		
		$this.tableManager.createTable();

		$("#btn-search").click(function(){
			$this.tableManager.searchData();
		});
		
	}
	
	this.tableManager = {
			createTable : function() {
				/* 데이터 ajax 버전 */
				let header = $("meta[name='_csrf_header']").attr('content');
				let token = $("meta[name='_csrf']").attr('content');
				
				let columnArr = [
		        	 		{
		        	 			"title": "등록일"
			        		 	, "data": "reg_dt"
			        		 	, "name": "reg_dt"
			        		 	, "visible": false
		        		 	}, // 최초 로딩시 정렬 컬럼 (hidden상태)
				        	{
		        		 		"title": "프로필"
		        		 		, "data": "file_path"
		        		 		, "name": "file_path"
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
				
				let requestUrl = PAGE_URL + "/selectCatGroupMemberList";
				let tableId = "catList";
				if(groupFlag == "grp"){
					columnArr.splice(5,1);
					requestUrl = PAGE_URL + "/selectCatGroupList";
					tableId = "catGroupList";
				}
				
				$this.$table = $('#'+tableId).DataTable({
					order : [[0, 'asc']],// 최초 로딩시 정렬 컬럼 설정
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
						url : requestUrl,
						type : "POST",
						data : function (d) {
							//d는 그리드 정보 객체, paging시 필요
							//검색정보
							let data = $("#search-form").serializeObject();
							data['cat_cd'] = Number(data['cat_cd']);
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
				        let row = $this.tableManager.getSelectedRows();
						location.href = "/cat/catProfile/" + row.cat_cd;
				    }
				} );

			},
			getSelectedRows : function() {	//선택한 행 가져오기
				return $this.$table.rows('.selected').data()[0];
			},
			searchData : function() {	//검색
				return $this.$table.ajax.reload();
			}
			
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>