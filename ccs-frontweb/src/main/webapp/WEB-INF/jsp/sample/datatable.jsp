<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">
	<form id="search-form" class="form-group m-3">
		<div class="row">
			<div class="form-group col-md-1">
	         	<label for="name" class="form-label">이름</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="name" name="name" type="text">
	        </div>
	        <div class="form-group col-md-1">
	         	<label for="age" class="form-label">나이</label>
        	</div>
	        <div class="form-group col-md-3">
	           	<input class="form-control" id="age" name="age" type="text">
	        </div>
	        <div class="form-group col-md-4">
	         	<button class="btn btn-secondary my-2 my-sm-0 float-right" type="button" id="btn-search">검색</button>
	        </div>
	    </div>
	</form>	    
</div>
	<div class="col-md-12">
		<table id="tableId"></table>
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
	
		$("#btn-search").click(function(){
			$this.tableManager.searchData();
		});
		
		//$this.tableManager.createTable();
		//$this.tableManager.createSearchTable();
		$this.tableManager.createSearchTablePaging();
		
	}
	
	this.tableManager = {
			createTable : function() {
				/* data 하드코딩  버전 */
				$this.$table = $('#tableId').DataTable({
					destroy: true,//테이블 파괴가능
					bPaginate: true, //페이징처리
					bLengthChange: true, // n개씩보기
					lengthMenu : [ [10, 25, 50, -1], [10, 25, 50, "All"] ], // 10/25/50/All 개씩보기
					bAutoWidth: false, //자동너비
					ordering: true, //칼럼별 정렬
					searching: false, //검색기능
		 			select: true,
		 			/* $('#example').DataTable( {
					    	select: 'single' or 'multi'
						} ); */
			        columns: [
			            {"title":"이름", "data": "name", "name": "name", "visible": true},
			            {"title":"나이", "data": "age", "name": "age", "visible": true}, 
			            {"title":"도시", "data": "city", "name": "city", "visible": true}
			        ],
			        data : [
			        		{name: "김지영", age : 28, "city": "서울"}
				        	, {name: "홍영표", age : 28, "city": "경기도"}
				        	, {name: "김두남", age : 4, "city": "서울"}
				        	]
			    });
			},
			createSearchTablePaging : function() {
				/* 데이터 ajax 버전 */
				let header = $("meta[name='_csrf_header']").attr('content');
				let token = $("meta[name='_csrf']").attr('content');
				
				$this.$table = $('#tableId').DataTable({
					order: [[0, 'desc']],// 최초 로딩시 정렬 컬럼 설정
					destroy: true,//테이블 파괴가능
				    processing : true,
				    serverSide : true, 
					bPaginate: true, //페이징처리
					lengthChang : true,
					bLengthChange: true, // n개씩보기
					lengthMenu : [ [3, 10, 25, 50, -1], [3, 10, 25, 50, "All"] ], // * 실 페이지 설정  값 : 10/25/50/All 개씩보기
					ordering: true, //칼럼별 정렬
					filter : false,
					bFilter : false,
					searching: false, //검색기능 (*false로 두고 ajax로 구현하도록한다)
				    bAutoWidth: false, //자동너비
				    select: true,
		 			/* $('#example').DataTable( {
					    	select: 'single' or 'multi'
						} ); */
					ajax: {
						url:"/sample/datatable/searchPaging",
						type: "POST",
						data: function (d) {
							//d는 그리드 정보 객체, paging시 필요
							//검색정보
							let a = $("#search-form").serializeObject()
							//그리드 정보 + 검색정보							
			                return JSON.stringify($.extend({}, a, d));
			            },
						dataType: "JSON",
						contentType: "application/json; charset=utf-8",
						beforeSend: function(xhr){
					        xhr.setRequestHeader(header, token);
					    }
					},
			        columns: [
			        	 {"title": "no", "data": "no", "name": "no", "visible": false}, // 최초 로딩시 정렬 컬럼 (hidden상태)
			        	 {"title": "no", "data": null, "name":"numbering", "orderable": false, "visible": true},//넘버링 컬럼
			        	 {"title": "이름", "data": "name", "name": "name", "visible": true},
				         {"title": "나이", "data": "age", "name": "age", "visible": true}, 
				         {"title": "도시", "data": "city", "name": "city", "visible": true}
			        ]
			    });
				
				//https://datatables.net/manual/events#Listening-for-events
				//https://datatables.net/reference/api/columns()
				$this.$table.on( 'draw.dt', function () {
			        var PageInfo = $('#tableId').DataTable().page.info();
			        var colIdx = $this.$table.column('numbering:name').index(); //.column('넘버링 컬럼의 name:name')
			        $this.$table.column(colIdx, { page: 'current' }).nodes().each( function (cell, i) {
			                cell.innerHTML = i + 1 + PageInfo.start;
			            } );
			        } );
			
			},
			createSearchTable : function() {
				/* 데이터 ajax 버전 */
				let header = $("meta[name='_csrf_header']").attr('content');
				let token = $("meta[name='_csrf']").attr('content');
				
				$this.$table = $('#tableId').DataTable({
					destroy: true,//테이블 파괴가능
				    //processing : true,
				    //serverSide : true, 
					bPaginate: false, //페이징처리
					lengthChang : true,
					bLengthChange: true, // n개씩보기
					lengthMenu : [ [1, 10, 25, 50, -1], [1, 10, 25, 50, "All"] ], // 10/25/50/All 개씩보기
					ordering: true, //칼럼별 정렬
					filter : false,
					bFilter : false,
					searching: false, //검색기능
				    bAutoWidth: false, //자동너비
				    select: true,
		 			/* $('#example').DataTable( {
					    	select: 'single' or 'multi'
						} ); */
					ajax: {
						url:"/sample/datatable/search",
						type: "POST",
						data: function (d) {
			                d.name = $("#name").val();
			                d.age = $("#age").val();
			                return JSON.stringify(d);
			            },
						dataType: "JSON",
						contentType: "application/json; charset=utf-8",
						beforeSend: function(xhr){
					        xhr.setRequestHeader(header, token);
					    }
					},
			        columns: [
			        	 {"title":"이름", "data": "name", "name": "name", "visible": true},
				         {"title":"나이", "data": "age", "name": "age", "visible": true}, 
				         {"title":"도시", "data": "city", "name": "city", "visible": true}
			        ]
			    });
			},
			getSelectedRows : function() {	//선택한 행 가져오기
				$this.$table.rows('.selected').data();
			},
			getAllRows : function() {	//전체 행 가져오기
				$this.$table.rows().data();
			},
			searchData : function() {	//검색
				$this.$table.ajax.reload();
			},
			clearData : function() {	//데이터 모두 제거
				$this.$table.clear().draw();
			}
			
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>