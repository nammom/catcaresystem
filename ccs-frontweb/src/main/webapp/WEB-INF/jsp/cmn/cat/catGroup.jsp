<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<%@ include file="/WEB-INF/jsp/cmn/menu/manageNav.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<form onsubmit="return false" id="catGroup-search-form" class="form-group m-3">
			<input type="hidden" id="target_cd" name="target_cd" value="<c:out value="${target_cd}" />">
			<input type="hidden" id="groupFlag" name="groupFlag" value="<c:out value="${groupFlag}" />">
		</form>	    
	</div>
	<div class="col-md-12">
		<div class="row">
			<button class="btn btn-secondary my-2 my-sm-0" type="button" id="btn-add">등록</button>
			<button class="btn btn-secondary my-2 my-sm-0" type="button" id="btn-delete">삭제</button>
		</div>
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
let _catGroupPage = null;

$(document).ready(function() {
	_catGroupPage = new fn_catGroupPage();
	_catGroupPage.initialize();
});

function fn_catGroupPage() {
	let $this = this;
	let PAGE_URL = "/cat/catGroup";
	let TARGET_CD, GROUPFLAG, DEFAULT_IMG_URL;
	
	this.$table;
	this.initialize = function() {
		$this.initData();
		
		$("#btn-add").click(function(){
			$this.searchManager.openSearchModal();
		});
		
		$("#btn-delete").click(function(){
			$this.tableManager.deleteRow();
		});
	}
	
	this.initData = function() {
		TARGET_CD = $("#target_cd").val();
		GROUPFLAG = $("#groupFlag").val();
		
		if (GROUPFLAG == "cat") {
			DEFAULT_IMG_URL = "/images/cmn/basic_cat_profile.jpg";
		} else if (GROUPFLAG == "grp") {
			DEFAULT_IMG_URL = "/images/cmn/basic_cat_grp_profile.jpg";
		}
		$this.tableManager.createTable();
		
	}
	
	this.tableManager = {
		tableId : "catList",
		createTable : function() {
			/* 데이터 ajax 버전 */
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
			        		 							return '<img class="img-profile-s" src="' + DEFAULT_IMG_URL + '"/>';
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
			
			if(GROUPFLAG == "grp"){
				//그룹인 경우 품종 컬럼 제거
				let colIdx = $.ccs.findIndexByKey(columnArr, "name", "cat_kind_nm"); 
				columnArr.splice(colIdx,1);
				$this.tableManager.tableId = "catGroupList";
			}
			
			$this.$table = $('#' + $this.tableManager.tableId).DataTable({
				destroy : true,//테이블 파괴가능
				lengthChang : true,
				bLengthChange : true, // n개씩보기
				lengthMenu : [ [10, 25, 50, -1], [10, 25, 50, "All"] ], // * 실 페이지 설정  값 : 10/25/50/All 개씩보기
				ordering : true, //칼럼별 정렬
			    select: {
			        style:    'os',
			        selector: 'td:not(:first-child)'
			    },
			    processing : false,
			    serverSide : false, 
				bPaginate : false, //페이징처리
				filter : false,
				bFilter : false,
				searching : false, //검색기능 (*false로 두고 ajax로 구현하도록한다)
			    bAutoWidth : false, //자동너비
				ajax: {
					url : PAGE_URL + "/selectCatGroupList",
					type : "POST",
					data : function (d) {
						//d는 그리드 정보 객체, paging시 필요
						//검색정보
						let data = $("#catGroup-search-form").serializeObject();
						data['target_cd'] = Number(data['target_cd']);
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
		        let row = $this.tableManager.getSelectedRows();
		        if(row[0]['cat_cd']){
					location.href = "/cat/catProfile/" + GROUPFLAG + "/" + row[0]['cat_cd'];
		        }
			} );
		},
		getSelectedRows : function() {	//선택한 행 가져오기
			return $this.$table.rows('.selected').data().toArray();
		},
		searchData : function() {	//검색
			return $this.$table.ajax.reload();
		},
		deleteRow : function(){	//행삭제
			let rowsArr = this.getCheckedRows();
			if(!rowsArr.length){
				alert("선택한 행이 없습니다.");
			}else{
				$.ccs.confirm("삭제하시겠습니까?", function(){
					let deleteRows= $this.tableManager.getCheckedRowsData();
					$.ccs.ajax({
						url : PAGE_URL + "/delete"
						, data : $this.tableManager.getDeleteData(deleteRows)
						, success : function(data){
							alert("삭제되었습니다.");
							$this.tableManager.searchData();
						}
					});
				});
			}
		},
		getAllRows : function() {	//전체 행 가져오기
			return $this.$table.rows().data();
		},
		getCheckedRows : function() {	//체크박스 체크된 행 가져오기
			let rowsArr = []; 
			$.each($("input[name='input-check']:checked"),function(i,e){
				rowsArr.push($(e).closest('tr').index());
			});
			return rowsArr;
		},
		getCheckedRowsData : function() {	//체크박스 체크된 행 데이터 가져오기
			let arr = this.getCheckedRows();
			if(arr.length > 0){
				return $this.$table.rows(arr).data().toArray();
			};
			return null;
		},
		getDeleteData : function(deleteRows){	//삭제 데이터 가져오기
			let data = $("#catGroup-search-form").serializeObject();
			data['target_cd'] = Number(data['target_cd']);
			data['deleteList'] = deleteRows;
			return data;
		}
	}
	
	this.searchManager = {
		openSearchModal : function(){
			let url = "/search/cat?groupFlag=" + GROUPFLAG + "&target_cd=" + Number(TARGET_CD); 
			let option = {
					id : "searchCatModal",
					title : "고양이 검색",
					button : [{"title" : "저장", id : "btn-save", "click" : this.save}]
				};
			$.ccs.modal.create(url, option);
		},
		getSaveData : function(arr){
			let data = $("#catGroup-search-form").serializeObject();
			data['target_cd'] = Number(data['target_cd']);
			//이미 등록된 고양이 제거 
			let catCdArr = $.ccs.pluck($.ccs.table.getAllRows($this.$table), "cat_cd");
			let saveList = arr.filter(function(e, idx){
				if(catCdArr.indexOf(e['cat_cd']) < 0){
					return e;
				}
			});
			data['saveList'] = saveList;
			return data;
		},
		save : function(){	//저장
			
			if(!_modal.tableManager.getCheckedRows().length){
				alert("선택한 행이 없습니다.");
			}else{
				let selectedList = _modal.tableManager.getCheckedRowsData();
				let data = $this.searchManager.getSaveData(selectedList);
				if(!data['saveList'].length){
					$this.searchManager.saveCallback();
				}else{
					$.ccs.ajax({
						url : PAGE_URL + "/save"
						, data : data
						, success : function(data){
							$this.searchManager.saveCallback();
						}
					});
				}
			}
		},
		saveCallback : function(){
			alert("저장되었습니다.");
			$this.tableManager.searchData();
			$.ccs.modal.close("searchCatModal");
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>