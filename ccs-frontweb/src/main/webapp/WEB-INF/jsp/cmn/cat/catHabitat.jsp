<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<form id="catHabitat-search-form" class="form-group m-3">
			<input type="hidden" id="cat_cd" name="cat_cd" value="<c:out value="${cat_cd}" />">
		</form>	    
	</div>
	<div class="col-md-12">
		<div class="row">
			<button class="btn btn-secondary my-2 my-sm-0" type="button" id="btn-add">등록</button>
			<button class="btn btn-secondary my-2 my-sm-0" type="button" id="btn-delete">삭제</button>
		</div>
	</div>
	<div class="col-md-12">	
		<table id="catHabitatList"></table>
    </div>
</div>
<script>
let _catGroupPage = null;

$(document).ready(function() {
	_catHabitatPage = new fn_catHabitatPage();
	_catHabitatPage.initialize();
});

function fn_catHabitatPage() {
	let $this = this;
	let PAGE_URL = "/cat/catHabitat";
	let cat_cd;
	
	this.$table;
	this.initialize = function() {
		cat_cd = $("#cat_cd").val();
		
		$this.tableManager.createTable();

		$("#btn-add").click(function(){
			$this.searchManager.openSearchModal();
		});

		$("#btn-delete").click(function(){
			$this.tableManager.deleteRow();
		});
	}
	
	this.tableManager = {
		createTable : function() {
			/* 데이터 ajax 버전 */
			let header = $("meta[name='_csrf_header']").attr('content');
			let token = $("meta[name='_csrf']").attr('content');
			
			$this.$table = $('#catHabitatList').DataTable({
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
					url : PAGE_URL + "/selectCatHabitatList",
					type : "POST",
					data : function (d) {
						//d는 그리드 정보 객체, paging시 필요
						//검색정보
						let data = $("#catHabitat-search-form").serializeObject();
						data['cat_cd'] = Number(data['cat_cd']);
						
						//그리드 정보 + 검색정보							
		                return JSON.stringify(data);
		            },
					dataType : "JSON",
					contentType : "application/json; charset=utf-8",
					beforeSend : function(xhr){
				        xhr.setRequestHeader(header, token);
				    }
				},
		        columns : [
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
		    });
			
			$this.$table.on( 'select', function ( e, dt, type, indexes ) {
		        let row = $this.tableManager.getSelectedRows();
		        if(row[0]['habitat_cd']){
					location.href = "/cat/habitatProfile/" + row['habitat_cd'];
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
			let data = $("#catHabitat-search-form").serializeObject();
			data['cat_cd'] = Number(data['cat_cd']);
			data['deleteList'] = deleteRows;
			return data;
		}
	}
	
	this.searchManager = {
		openSearchModal : function(){
			let url = "/search/habitat/" + Number(cat_cd);
			let option = {
					id : "searchHabitatModal",
					title : "서식지 검색",
					button : [{"title" : "저장", id : "btn-save", "click" : this.save}]
				};
			$.ccs.modal.create(url, option);
		},
		getSaveData : function(arr){
			let data = $("#catHabitat-search-form").serializeObject();
			data['cat_cd'] = Number(data['cat_cd']);
			//이미 등록된 고양이 제거 
			let catCdArr = $.ccs.pluck($this.tableManager.getAllRows(), "habitat_cd");
			let saveList = arr.filter(function(e, idx){
				if(catCdArr.indexOf(e['habitat_cd']) < 0){
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
			$.ccs.modal.close("searchHabitatModal");
		}
	}
	
	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>