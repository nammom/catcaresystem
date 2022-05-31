<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<form id="search-form" class="form-group m-3">
		<input type="hidden" id="target_cd" name="target_cd" value="<c:out value="${target_cd}" />">
		<div class="row">
			<div class="form-group col-md-1">
	         	<label for="nickname" class="form-label">닉네임</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="nickname" name="nickname" type="text">
	        </div>
	        <div class="form-group col-md-1">
	         	<label for="userid" class="form-label">아이디</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="userid" name="userid" type="text">
	        </div>
	        <div class="form-group col-md-4">
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
let _mypage = null;

$(document).ready(function() {

	_mypage = new fn_page();
	_mypage.initialize();
});
function fn_page() {
	let $this = this;
	this.$table;
	this.initialize = function() {
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
				
				$this.$table = $('#careList').DataTable({
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
						url:"/cat/catProfile/careList/selectData",
						type: "POST",
						data: function (d) {
							//d는 그리드 정보 객체, paging시 필요
							//검색정보
							let data = $("#search-form").serializeObject();
							data['target_cd'] = Number(data['target_cd']);
							d['data'] = data;
							
							//그리드 정보 + 검색정보							
			                return JSON.stringify(d);
			            },
						dataType: "JSON",
						contentType: "application/json; charset=utf-8",
						beforeSend: function(xhr){
					        xhr.setRequestHeader(header, token);
					    }
					},
			        columns: [
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
			        		 							return '<img class="img-profile-s" src="/images/cmn/basic_user_profile.jpg"/>';
			        		 						}
								            	}
			        		 	},
						        {
			        		 		"title": "닉네임"
			        		 		, "data": "nickname"
			        		 		, "name": "nickname"
			        		 	}, 
						        {
			        		 		"title": "아이디"
			        		 		, "data": "userid"
			        		 		, "name": "userid"
			        		 	},
						        {
			        		 		"title": "user_cd"
			        		 		, "data": "user_cd"
			        		 		, "name": "user_cd"
			        		 		, "visible": false
			        		 	}
			        ]
			    });
				
				$this.$table.on( 'select', function ( e, dt, type, indexes ) {
				    if ( type === 'row' ) {
				        let row = $this.tableManager.getSelectedRows();
						location.href = "/user/userProfile/" + row.user_cd;
				    }
				} );

			},
			getRowData : function(obj){
				return $this.$table.rows(obj).data()[0];
			},
			getSelectedRows : function() {	//선택한 행 가져오기
				return $('#careList').DataTable().rows('.selected').data()[0];
			},
			searchData : function() {	//검색
				return $this.$table.ajax.reload();
			}
			
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>