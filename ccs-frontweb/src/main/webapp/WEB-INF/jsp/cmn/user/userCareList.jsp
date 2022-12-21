<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
<div class="col-md-12">	
		<nav>
		  <div class="nav nav-tabs" id="nav-tab" role="tablist">
		    <button class="nav-link active" id="nav-cat-tab" data-content="cat" data-bs-toggle="tab" data-bs-target="#cat-content" type="button" role="tab" aria-controls="cat-content" aria-selected="true">고양이</button>
		    <button class="nav-link" id="nav-habitat-tab" data-content="habitat" data-bs-toggle="tab" data-bs-target="#habitat-content" type="button" role="tab" aria-controls="habitat-content" aria-selected="false">서식지</button>
		  </div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
 		 	<!-- 고양이 돌봄 리스트 -->
		  <div class="tab-pane fade show active" id="cat-content" role="tabpanel" aria-labelledby="nav-cat-tab">
          		<div class="col-md-12">
					<form onsubmit="return false" id="cat-care-form" class="form-group m-3">
						<input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
						<div class="row">
							<div class="form-group col-md-1">
								<label for="sido" class="form-label">지역</label>
							</div>
							<div class="form-group col-md-3">
								<select id="sido_cd" name="sido_cd" data-code="sido" class="form-control area-select">
				                	<ccs:area code="sido" includeAll="전체"/>
				                </select>
							</div>
							<div class="form-group col-md-3">
								<select id="sigungu_cd" name="sigungu_cd" data-code="sigungu" class="form-control area-select">
				                	<ccs:area code="sigungu" includeAll="전체"/>
				                </select>
							</div>
							<div class="form-group col-md-3">
								<select id="dong_cd" name="dong_cd" data-code="dong" class="form-control area-select last-select">
				                	<ccs:area code="dong" includeAll="전체" /> 
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
					        <div class="form-group col-md-2">
					         	<label for="cat_cd" class="form-label">고유코드</label>
				        	</div>
					        <div class="form-group col-md-2">
					           	<input class="form-control" id="cat_cd" name="cat_cd" type="text">
					        </div>
					        <div class="form-group col-md-2">
								<select id="group_yn" name="group_yn" class="form-control">
				                	<option value="N" selected>개별</option>
				                	<option value="Y">무리</option>
				                </select> 
							</div>
					        <div class="form-group col-md-2">
					         	<button class="search btn btn-secondary my-2 my-sm-0 float-right" type="button" id="btn-search" data-content="cat">검색</button>
					        </div>
					    </div>
					</form>	    
				</div>
				<div class="col-md-12" id="catCareList-wrap">
					<table id="catCareList"></table>
				</div>
				<div class="col-md-12" id="catGrpCareList-wrap" style="display:none;">
					<table id="catGrpCareList"></table>
				</div>
			</div>
				<!-- 서식지 돌봄 리스트 -->
			<div class="tab-pane fade" id="habitat-content" role="tabpanel" aria-labelledby="nav-habitat-tab">
			       	<div class="col-md-12">
				<form onsubmit="return false" id="habitat-care-form" class="form-group m-3">
					<input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
					<div class="row">
						<div class="form-group col-md-1">
							<label for="sido" class="form-label">지역</label>
						</div>
						<div class="form-group col-md-3">
							<select id="sido_cd" name="sido_cd" data-code="sido" class="form-control area-select">
			                	<ccs:area code="sido" includeAll="전체"/>
			                </select>
						</div>
						<div class="form-group col-md-3">
							<select id="sigungu_cd" name="sigungu_cd" data-code="sigungu" class="form-control area-select">
			                	<ccs:area code="sigungu" includeAll="전체"/>
			                </select>
						</div>
						<div class="form-group col-md-3">
							<select id="dong_cd" name="dong_cd" data-code="dong" class="form-control area-select last-select">
			                	<ccs:area code="dong" includeAll="전체" /> 
			                </select> 
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-1">
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
				        <div class="form-group col-md-2">
				         	<button class="search btn btn-secondary my-2 my-sm-0 float-right" type="button" id="btn-search" data-content="habitat">검색</button>
				        </div>
				    </div>
				</form>	    
			</div>
			<div class="col-md-12">
				<table id="habitatCareList"></table>
			</div>
			      	</div>
		  </div>
		  </div>
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
	let PAGE_URL = "/user/care";
	let USER_CD;
	
	this.initialize = function() {
		$this.initData();
		
		//탭 버튼
		$(".nav-link").click(function(){
			let content = $(this).data("content");
			let tableId = $this.tableManager.getTableId(content);
			//테이블 show, hide
			$this.styleManager.setTable(tableId);

			//테이블 데이터 조회
			//let $table = $this.tableManager.table[tableId];
			//$.ccs.table.reloadData($table);
			
			//tab style 활성화
			$this.styleManager.setTab(this);
			
		});
		
		//검색 버튼
		$(".search").click(function(){
			let content = $(this).data("content");
			let tableId = $this.tableManager.getTableId(content);
			let $table = $this.tableManager.table[tableId];
			//테이블 데이터 조회
			$.ccs.table.reloadData($table);
		});
		
		//고양이 탭 > 폼 > 그룹여부 select 박스
		$("#group_yn").change(function(){
			let tableId = $this.tableManager.getTableId("cat");
			let $table = $this.tableManager.table[tableId];
			$.ccs.table.reloadData($table);
			//테이블 show, hide
			$this.styleManager.setTable(tableId);
		});
	}
	
	this.initData = function() {
		$this.formManager.setData();

		//table 생성
		$this.tableManager.createTable("catCareList");
		$this.tableManager.createTable("catGrpCareList");
		$this.tableManager.createTable("habitatCareList");
		//table event 추가
		$this.tableManager.addTableEvent();
	}
	
	this.tableManager = {
		table :	{ //dataTable 객체
			catCareList : null,
			catGrpCareList : null,
			habitatCareList : null,
		},
		getTableId : function(contentId) {	//table Id 조회
			let tableId;
			switch (contentId) {
				case "cat" : 
					if ($("#group_yn").val() == "Y") {
						tableId = "catGrpCareList";
					} else {
						tableId = "catCareList";
					}
					break;
				case "habitat" :
					tableId = "habitatCareList";
					break;
			}
			return tableId;
		},
		createTable : function(tableId) {	// table 생성
			let header = $("meta[name='_csrf_header']").attr('content');
			let token = $("meta[name='_csrf']").attr('content');
			let tableInfo = $this.tableManager.tableInfo[tableId];
			
			$this.tableManager.table[tableId] = $("#" + tableId).DataTable({
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
					url : tableInfo['url'],
					type : "POST",
					data : function (d) {
						//d는 그리드 정보 객체, paging시 필요
						//검색정보
						let formId = tableInfo['form'];
						let data = $("#" + formId).serializeObject();
						if(data['user_cd']) data['user_cd'] = Number(data['user_cd']);
						if(data['cat_cd']) data['cat_cd'] = Number(data['cat_cd']);
						if(data['habitat_cd']) data['habitat_cd'] = Number(data['habitat_cd']);
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
		        columns : tableInfo['columns']  
		    });
		},
		addTableEvent : function() {
			$this.tableManager.table['catCareList'].on( 'select', function ( e, dt, type, indexes ) {
			    if ( type === 'row' ) {
			        let row = $.ccs.table.getSelectedRows($this.tableManager.table['catCareList']);
			        if(row[0]['cat_cd']){
						$this.locationManager.catProfile(row[0]['cat_cd'], row[0]['group_yn']);
			        }
			    }
			} );
			
			$this.tableManager.table['catGrpCareList'].on( 'select', function ( e, dt, type, indexes ) {
			    if ( type === 'row' ) {
			        let row = $.ccs.table.getSelectedRows($this.tableManager.table['catGrpCareList']);
			        if(row[0]['cat_cd']){
						$this.locationManager.catGrpProfile(row[0]['cat_cd'], row[0]['group_yn']);
			        }
			    }
			} );
			
			$this.tableManager.table['habitatCareList'].on( 'select', function ( e, dt, type, indexes ) {
			    if ( type === 'row' ) {
			        let row = $.ccs.table.getSelectedRows($this.tableManager.table['habitatCareList']);
			        if(row[0]['habitat_cd']){
						$this.locationManager.habitatProfile(row[0]['habitat_cd'], row[0]['habitat_cd']);
			        }
			    }
			} );
		},
		tableInfo : {	
			/*	table 정보 	
				형식 : 
					tableId : {	url : String 
								, form : String
								, columns : Array
								}
			*/
			catCareList : { 
							url : PAGE_URL + "/cat/selectData"
							, form : "cat-care-form"
							, columns : [
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
							    		]
							},
    		catGrpCareList : {
    							url : PAGE_URL + "/cat/selectData"
    							, form : "cat-care-form"
    							, columns : [
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
			    							 							return '<img class="img-profile-s" src="/images/cmn/basic_cat_grp_profile.jpg"/>';
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
			    							 	}
			    							]
    						},
			habitatCareList : {
								url : PAGE_URL + "/habitat/selectData"
								, form : "habitat-care-form"
								, columns : [
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
											]
								}
		}		
	}
	
	this.locationManager = {
		catProfile : function(cat_cd) {
			location.href = "/cat/catProfile/cat/" + cat_cd;
		},
		catGrpProfile : function(cat_cd) {
			location.href = "/cat/catProfile/grp/" + cat_cd;
		},
		habitatProfile : function(habitat_cd) {
			location.href = "/manage/habitat/profile/" + habitat_cd;
		},
	}
	
	this.formManager = {
		setData : function() {
			//트리계층 select option생성
			$.ccs.cmnCodeOption.getAreaCodeList("#cat-care-form>div>.form-group>#sido_cd");
			$.ccs.cmnCodeOption.getAreaCodeList("#habitat-care-form>div>.form-group>#sido_cd");
		}
	}
	
	this.styleManager = {
		setTable : function (tableId) {	//table style 설정
			if (tableId == "catCareList") {
				$("#catCareList-wrap").show();
				$("#catGrpCareList-wrap").hide();
			} else if (tableId == "catGrpCareList") {
				$("#catCareList-wrap").hide();
				$("#catGrpCareList-wrap").show();
			}
		},
		setTab : function (tabObj) {	//tab style 설정
			//tab 활성화
			$(".nav-link.active").removeClass("active");
			$(tabObj).addClass("active");
			
			//연결 content div 활성화
			let targetContent = $(tabObj).data("bs-target");
			$(targetContent).addClass("active show");
			$("#nav-tabContent>.tab-pane:not(" + targetContent + ")").removeClass("active show");
		}
	}
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>