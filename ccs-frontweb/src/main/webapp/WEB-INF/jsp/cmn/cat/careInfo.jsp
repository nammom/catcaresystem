<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
	<form id="search-form" class="form-group m-3">
		<input type="hidden" id="cat_cd" name="cat_cd" value="<c:out value="${cat_cd}" />">
	</form>
	<div class="col-md-12">
		<div class="row">
			<button class="btn btn-secondary my-2 my-sm-0" type="button" id="btn-add">등록</button>
		</div>
	</div>
	<div class="col-md-12">
		<table id="careInfoList"></table>
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
	let PAGE_URL = "/cat/careInfo";
	let cat_cd;
	this.$table;
	this.initialize = function() {
		cat_cd = $("#cat_cd").val();
		$this.tableManager.createTable();
		
		$("#btn-add").click(function(){
			$this.actionManager.add();
		});
	}
	
	this.tableManager = {
			createTable : function() {
				/* 데이터 ajax 버전 */
				let header = $("meta[name='_csrf_header']").attr('content');
				let token = $("meta[name='_csrf']").attr('content');
				
				let columnArr = [
				        			{
				        				"title": "no"
				        				, "data": null
				        				, "name":"numbering"
				        				, "orderable": false
				        				, "visible": true
				        			},	//넘버링 컬럼
							        {
				        		 		"title": "catcare_cd"
				        		 		, "data": "catcare_cd"
				        		 		, "name": "catcare_cd"
				        		 		, "visible" : false
				        		 	}, 
							        {
				        		 		"title": "돌봄 종료/재개"
				        		 		, "data": "care_type_nm"
				        		 		, "name": "care_type_nm"
				        		 	}, 
							        {
				        		 		"title": "사유"
				        		 		, "data": "reason_nm"
				        		 		, "name": "reason_nm"
				        		 	},
							        {
				        		 		"title": "신청자"
				        		 		, "data": "nickname"
				        		 		, "name": "nickname"
				        		 	},
							        {
				        		 		"title": "user_cd"
				        		 		, "data": "user_cd"
				        		 		, "name": "user_cd"
				        		 		, "visible" : false
				        		 	},
				        	 		{
				        	 			"title": "신청일"
					        		 	, "data": "sub_dt"
					        		 	, "name": "sub_dt"
				        		 	}
				        		];
				
				let colIdx = $.ccs.findIndexByKey(columnArr, "name", "catcare_cd"); 
				
				$this.$table = $('#careInfoList').DataTable({
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
				
				//https://datatables.net/manual/events#Listening-for-events
				//https://datatables.net/reference/api/columns()
				$this.$table.on( 'draw.dt', function () {
			        var PageInfo = $('#careInfoList').DataTable().page.info();
			        var colIdx = $this.$table.column('numbering:name').index(); //.column('넘버링 컬럼의 name:name')
			        $this.$table.column(colIdx, { page: 'current' }).nodes().each( function (cell, i) {
			            cell.innerHTML = i + 1 + PageInfo.start;
			        } );
			    } );
				
				$this.$table.on( 'select', function ( e, dt, type, indexes ) {
				    if ( type === 'row' ) {
				        let row = $this.tableManager.getSelectedRows();
				        if(row[0]['catcare_cd']){
							location.href = PAGE_URL + "/detail/"+ row[0]['catcare_cd'];
				        }
				    }
				} );

			},
			getRowData : function(obj) {
				return $this.$table.rows(obj).data().toArray();
			},
			getSelectedRows : function() {	//선택한 행 가져오기
				return $this.$table.rows('.selected').data().toArray();
			},
			searchData : function() {	//검색
				return $this.$table.ajax.reload();
			}
			
	}
	
	this.actionManager = {
		add : function() {
			location.href = PAGE_URL + "/form/" + cat_cd;
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>