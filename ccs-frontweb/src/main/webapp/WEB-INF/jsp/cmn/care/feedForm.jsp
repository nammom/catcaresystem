<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<div class="col-md-6">
			<form onsubmit="return false" class="form-group contents" id="feed-form" method="post">
				<c:if test="${empty feed_cd}">
					<input type="hidden" id="cat_cd" name="cat_cd" value="<c:out value="${cat_cd}" />">
				</c:if>
				<input type="hidden" id="feed_cd" name="feed_cd" value="<c:out value="${feed_cd}" />">
				<input type="hidden" id="s_user_cd" name="s_user_cd" value="<c:out value="${_SESSION_USER_CD_}" />"/>
				<input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
				
				<div class="form-group">
		        	<label for="nickname" class="form-label mt-4">급여자</label>
		        	<input type="text" 
			        	id="nickname" name="nickname" 
			        	class="form-control"
			        	value="<c:out value="${_SESSION_USER_NM_}" />"
			        	disabled/>
		    	</div>
				<div class="form-group">
			        <label class="form-label mt-4">급여일시</label>
			        <input type="text" id="feed_dt" name="feed_dt" class="form-control datePicker-control startDate" placeholder="년-월-일(YYYY-MM-DD)" required readonly <c:if test="${not empty feed_cd}">disabled</c:if>>
			        <input type="number" id="feed_time1" name="feed_time1" class="form-control" placeholder="시(HH)" min="1" max="24" maxlength="2" required <c:if test="${not empty feed_cd}">disabled</c:if>>
			        <input type="number" id="feed_time2" name="feed_time2" class="form-control" placeholder="분(mm)" min="0" max="59" required <c:if test="${not empty feed_cd}">disabled</c:if>>
			    </div>
			    <c:if test="${not empty feed_cd}">
				    <div class="form-group">
			        <label for="cat_cd" class="form-label mt-4">고양이 고유코드</label>
			        <input type="text" 
			        	id="cat_cd" name="cat_cd" 
			        	 value="<c:out value="${cat_cd}" />"
			        	class="form-control" 
			        	disabled/>
			        </div>
		        </c:if>
		    </div>
			</form>
			<form onsubmit="return false" id="feed-info-form">
				<c:if test="${empty feed_cd}">
					<div class="col-md-12 catList-btn-div">
						<button class="btn btn-secondary btn-sm btn-row-add" type="button" data-table="catList">추가</button>
						<button class="btn btn-secondary btn-sm btn-row-delete" type="button" data-table="catList">삭제</button>
					</div>
				    <div class="col-md-12">
				    	<label class='title'>급여고양이</label>
						<table id="catList"></table>
					</div>
				</c:if>	
				<div class="col-md-12">
					<button class="btn btn-secondary btn-sm btn-row-add" type="button" data-table="mainFeedList">추가</button>
					<button class="btn btn-secondary btn-sm btn-row-delete" type="button" data-table="mainFeedList">삭제</button>
				</div>
				<div class="col-md-12">
					<label class='title'>주식</label>
					<table id="mainFeedList"></table>
				</div>
				<div class="col-md-12">
					<button class="btn btn-secondary btn-sm btn-row-add" type="button" data-table="snackList">추가</button>
					<button class="btn btn-secondary btn-sm btn-row-delete" type="button" data-table="snackList">삭제</button>
				</div>		
			    <div class="col-md-12">
			    	<label class='title'>간식</label>
					<table id="snackList"></table>
				</div>
				<div class="col-md-12">
					<button class="btn btn-secondary btn-sm btn-row-add" type="button" data-table="etcList">추가</button>
					<button class="btn btn-secondary btn-sm btn-row-delete" type="button" data-table="etcList">삭제</button>
				</div>	
			    <div class="col-md-12">
			    <label class='title'>기타</label>
					<table id="etcList"></table>
				</div>
			</form>
			<div class="form-group">
				<div class="form-check">
      				<input type="checkbox" name="feed_cd1" value="400" class='form-check-input' <c:if test="${not empty feed_cd}">disabled</c:if>> 
      				<label class='form-check-label'>물</label>
      			</div>
			</div>
			<button type="button" id="btn-list" class="btn btn-warning">목록</button>
			<c:if test="${not empty feed_cd }">
		    	<button type="button" id="btn-save" class="btn btn-warning">수정</button>
		    	<button type="button" id="btn-delete" class="btn btn-warning">삭제</button>
		    </c:if>
		    <c:if test="${empty feed_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">저장</button>
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
	let PAGE_URL = "/care/feed/form";
	let CAT_CD, FEED_CD, S_USER_CD, USER_CD;
	
	this.initialize = function() {
		
		$this.initData();
		
		//테이블 행 추가
		$(".btn-row-add").click(function(){
			let tableId = $(this).data("table");
			if(tableId != "catList"){
				let $table = $this.tableManager.getTable($(this).data("table"));
				let data = $this.tableManager.getAddData($table);
				$.ccs.table.addRow($table, data);
			}else{
				$this.searchManager.openSearchModal();
			}
		});
		
		//테이블 행 삭제
		$(".btn-row-delete").click(function(){
			let $table = $this.tableManager.getTable($(this).data("table"));
			$.ccs.table.deleteRow($table);
		});
		
		//폼 등록(수정)
		$("#btn-save").click(function(){
			$this.formManager.save();
		});
		
		//폼 삭제
		$("#btn-delete").click(function(){
			$this.formManager.deleteData();
		});
 		
		//목록으로
		$("#btn-list").click(function(){
			$this.locationManager.list();
		});
	}
	
	this.initData = function() {
		CAT_CD = $("#cat_cd").val()? Number($("#cat_cd").val()) : "";
		FEED_CD = $("#feed_cd").val()? Number($("#feed_cd").val()) : "";
		S_USER_CD = $("#s_user_cd").val();
		
		//급여일자를 오늘 일자로 설정
		let feedDatepicker = $("#feed_dt").datepicker().data("datepicker");
		feedDatepicker.selectDate(new Date());
		
		$this.formManager.getData();			
	}
	
	this.formManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : FEED_CD ? {"feed_cd" : FEED_CD} : {"cat_cd" : CAT_CD}
				, success : function(data){
					if(FEED_CD){
						$this.formManager.setData(data['feedDetail']);
					}
					$this.tableManager.createTables();
					$this.tableManager.setData(data);
				}
			});
		},
		setData : function(data){
			//form 초기화
			$("#feed-form")[0].reset();
			//form 정보
			$("#feed-form").bindJson(data);
			//USER_CD set
			USER_CD = $("#user_cd").val();
			//CAT_CD set
			CAT_CD = $("#cat_cd").val()? Number($("#cat_cd").val()) : "";
			
			if(S_USER_CD == USER_CD){
				$("#feed_dt").attr("disabled", false);
				$("#feed_time1").attr("disabled", false);
				$("#feed_time2").attr("disabled", false);
				$("input[name='feed_cd1']").attr("disabled", false);
			}else{
				$("#btn-save").hide();
				$("#btn-delete").hide();
				$(".btn-add").hide();
				$(".btn-delete").hide();
			}
		},
		validate : function(data){
			if(!data['feed_cd'] && !data['catList'].length){
				alert("고양이를 등록해주세요.");
				return false;
			}
			if(!(data['feed_cd1'] || data['feedDetailList'].length)) {
				alert("급여정보를 등록해주세요.");
				return false;
			}
			if(!$("#feed-form").valid()){
				 return false;
			}
			if(!$("#feed-info-form").valid()){
				return false;
			}
			return true;
		},
		save : function() {
			let _data = this.getSaveData();
			if($this.formManager.validate(_data)){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, data : _data
					, success : function(data){
						alert("저장되었습니다.");
						if(FEED_CD){
							$this.formManager.getData(FEED_CD);	
						}else{
							$this.locationManager.list();
						}
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#feed-form").serializeObject();
			if(jsonData['feed_cd']){
				jsonData['feed_cd'] = Number(jsonData['feed_cd']);
			}
			if($("input[name='feed_cd1']").is(":checked")){
				jsonData['feed_cd1'] = $("input[name='feed_cd1']").val();
			}
			let tables = $this.tableManager.table;
			for(let key in tables){
				if(key == '$CAT_TABLE'){
					if(!jsonData['feed_cd']){
						jsonData['catList'] = $.ccs.table.getAllRows(tables[key]);
					}
				}else{
					let feedDetailList = jsonData['feedDetailList'] || [];
					jsonData['feedDetailList'] = feedDetailList.concat($.ccs.table.getAllRows(tables[key]));
				}
			}
			return jsonData;
		},
		deleteData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/delete"
				, data : {feed_cd : FEED_CD}
				, success : function(data){
					alert("삭제되었습니다.");
					$this.locationManager.list();			
				}
			});
		}
	}
	
	this.locationManager = {
		list : function() {
			let list_type = window['sessionStorage'].getItem("list_type");
			let target_type = window['sessionStorage'].getItem("target_type");
			let target_cd = window['sessionStorage'].getItem("target_cd");
			if(list_type && target_type && target_cd) {
				if(list_type == "list") {
					location.href = "/care/feed/" + target_type + "/" + target_cd;		
				} else {
					location.href = "/care/feed/calendar/" + target_type + "/" + target_cd;		
				}
			} else {
				alert("이전 목록이 없습니다. 홈 화면으로 돌아갑니다.");
				location.href = "/home";	
			}
		},
		detail : function(_feed_cd) {
			location.href = "/cat/feed/detail/" + _feed_cd;				
		}
	}
	
	this.tableManager = {
			table : {
				$CAT_TABLE : null
				, $MAIN_FEED_TABLE : null
				, $SNACK_TABLE : null
				, $ETC_TABLE : null
			},
			codeList : {
				FEED_SELECT_LIST : null
				, SNACK_SELECT_LIST : null
				, ETC_SELECT_LIST : null				
			},
			feedCd : {
				mainFeedList : "100"
				, snackList : "200"
				, etcList : "300"
				, waterList : "400"
			},
			getTable : function(tableId){
				let $table;
				switch(tableId){
					case "catList" : $table = this.table.$CAT_TABLE; 
						break;
					case "mainFeedList" : $table = this.table.$MAIN_FEED_TABLE; 
						break;
					case "snackList" : $table = this.table.$SNACK_TABLE; 
						break;
					case "etcList" : $table = this.table.$ETC_TABLE; 
						break;
				}
				return $table;
			},
			setData : function(data){
				//테이블  select 데이터 셋팅
				let feedCd2CodeMap = data['feedCd2CodeMap'];
				this.codeList.FEED_SELECT_LIST = feedCd2CodeMap[this.feedCd['mainFeedList']];
				this.codeList.SNACK_SELECT_LIST = feedCd2CodeMap[this.feedCd['snackList']];
				this.codeList.ETC_SELECT_LIST = feedCd2CodeMap[this.feedCd['etcList']];
				
				//급여 데이터 초기화
				$.ccs.table.clearData(this.table.$MAIN_FEED_TABLE);
				$.ccs.table.clearData(this.table.$SNACK_TABLE);
				$.ccs.table.clearData(this.table.$ETC_TABLE);
				$("input[name='feed_cd1']").attr("checked", false);
				
				//등록 폼인 경우 
				if(data['catList']){
					//고양이 정보 테이블 data 추가
					$.ccs.table.addRow(this.table.$CAT_TABLE, data['catList']);
					//그룹인 경우 추가,삭제 버튼 div 숨김
					if(data['catList'][0]['group_yn'] == 'Y'){
						$(".catList-btn-div").hide();
					}
				}
				
				//급여 정보 테이블 data 추가
				if(data['feedDetail'] && data['feedDetail']['feedDetailMap']){
					let feedDetailMap = data['feedDetail']['feedDetailMap'];
					if(feedDetailMap){
						let mainFeedList = feedDetailMap[this.feedCd['mainFeedList']];
						let snackList = feedDetailMap[this.feedCd['snackList']];
						let etcList = feedDetailMap[this.feedCd['etcList']];
						let waterList = feedDetailMap[this.feedCd['waterList']];
						
						if(mainFeedList){
							$.ccs.table.addRow(this.table.$MAIN_FEED_TABLE, mainFeedList);
						}
						if(snackList){
							$.ccs.table.addRow(this.table.$SNACK_TABLE, snackList);
						}
						if(etcList){
							$.ccs.table.addRow(this.table.$ETC_TABLE, etcList);
						}
						if(waterList){
							$("input[name='feed_cd1']").attr("checked", true);
						}
					}
				}
				
			},
			createTables : function(){
				let columnArr = [
					{
        		 		"title": ""
        		 		, "data": null
        		 		, "name": ""
        		 		, "render": function ( data, type, row, meta ) {
        		 						return '<input type="checkbox" name="input-check"/>';
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
        		 	}
				];
				this.table.$CAT_TABLE = this.createTable("catList", columnArr);
				
				let columnArr2;
				if(FEED_CD && S_USER_CD != USER_CD){
					columnArr2 = [
				        {
	        		 		"title": "종류"
	        		 		, "data": "feed_cd2_nm"
	        		 		, "name": "feed_cd2_nm"
	        		 	},
				        {
	        		 		"title": "제품명"
	        		 		, "data": "feed_nm"
	        		 		, "name": "feed_nm"
	        		 	},
				        {
	        		 		"title": "급여양"
	        		 		, "data": "feed_amt"
	        		 		, "name": "feed_amt"
	        		 	},
				        {
	        		 		"title": "내용"
	        		 		, "data": "comment"
	        		 		, "name": "comment"
	        		 	}
					];
					
				}else{
					columnArr2 = [
						{
	        		 		"title": ""
	        		 		, "data": null
	        		 		, "name": ""
	        		 		, "render": function ( data, type, row, meta ) {
	        		 						return '<input type="checkbox" name="input-check"/>';
						            	}
	        		 	},
	        		 	{
	        		 		"title": "feed_cd1"
	        		 		, "data": "feed_cd1"
	        		 		, "name": "feed_cd1"
	        		 		, "visible" : false
	        		 	},
				        {
	        		 		"title": "종류"
	        		 		, "data": "feed_cd2"
	        		 		, "name": "feed_cd2"
	       		 			, "render": function ( data, type, row, meta ) {
	       		 				let tableId = meta.settings.sTableId;
	       		 				return $this.tableManager.createSelectbox(tableId, data);
			            	}
	        		 	},
				        {
	        		 		"title": "제품명"
	        		 		, "data": "feed_nm"
	        		 		, "name": "feed_nm"
	       		 			, "render": function ( data, type, row, meta ) {
	   	 						return '<input type="text" name="feed_nm" caption="제품명" value=\"' +  (data||"") + '\" maxlength="60" required/>';
	   		            	}
	        		 	},
				        {
	        		 		"title": "급여양"
	        		 		, "data": "feed_amt"
	        		 		, "name": "feed_amt"
	       		 			, "render": function ( data, type, row, meta ) {
	   	 						return '<input type="text" name="feed_amt" caption="급여양" value=\"' +  (data||"") + '\" maxlength="60"/>';
	   		            	}
	        		 	},
				        {
	        		 		"title": "내용"
	        		 		, "data": "comment"
	        		 		, "name": "comment"
	       		 			, "render": function ( data, type, row, meta ) {
	   	 						return '<input type="text" name="comment" caption="내용" value=\"' + (data||"") + '\" maxlength="150" />';
	   		            	}
	        		 	}
					];
				}
				
				this.table.$MAIN_FEED_TABLE = this.createTable("mainFeedList", columnArr2);
				this.table.$SNACK_TABLE = this.createTable("snackList", columnArr2);
				this.table.$ETC_TABLE = this.createTable("etcList", columnArr2);
			},
			createSelectbox : function(tableId, data) {	//table에 맞는 select html 생성
				let codeList;
				switch(tableId){
					case "mainFeedList" : codeList = this.codeList.FEED_SELECT_LIST; 
						break;
					case "snackList" : codeList = this.codeList.SNACK_SELECT_LIST; 
						break;
					case "etcList" : codeList = this.codeList.ETC_SELECT_LIST; 
						break;
				}
				
				let html = "<select name=\"feed_cd2\">";
				html += $.ccs.cmnCodeOption.createOptionHtml(codeList, (data||""));
				html += "</select>";
				
				return html;
			},
			createTable : function(tableId, columnArr) {
				/* 데이터 ajax 버전 */
				let header = $("meta[name='_csrf_header']").attr('content');
				let token = $("meta[name='_csrf']").attr('content');
				
				let table = $('#' + tableId).DataTable({
					destroy : true,//테이블 파괴가능
					bLengthChange : false, // n개씩보기
				    select: {
				        style:    'os',
				        selector: 'td:not(:first-child)'
				    },
				    select : false,
					bPaginate : false, //페이징처리
					bInfo : false,
					searching : false, //검색기능 (*false로 두고 ajax로 구현하도록한다)
				    bAutoWidth : false, //자동너비
					data : [],
			        columns : columnArr
			    });
				
				return table;
				
			},
			getAddData : function(table){	//행 추가 데이터
				let tableId = table.table().node().id;
				let data = [
								{	
									"feed_cd1" : this.feedCd[tableId]
									, "feed_cd2" : ""
									, "feed_nm" : ""
									, "feed_amt" : ""
									, "comment" : ""
								}
							];
				return data;
			},
		}
		
		this.searchManager = {
			openSearchModal : function(){
				let url = "/search/cat?groupFlag=cat&target_cd=" + CAT_CD;
				let option = {
						id : "searchCatModal",
						title : "고양이 검색",
						button : [{"title" : "추가", id : "btn-add", "click" : this.add}]
					};
				$.ccs.modal.create(url, option);
			},
			getAddData : function(arr){
				//이미 등록된 고양이 제거 
				let catCdArr = $.ccs.pluck($.ccs.table.getAllRows($this.tableManager.table.$CAT_TABLE), "cat_cd");
				let saveList = arr.filter(function(e, idx){
					if(catCdArr.indexOf(e['cat_cd']) < 0){
						return e;
					}
				});
				return saveList;
			},
			add : function(){	//추가
				if(!_modal.tableManager.getCheckedRows().length){
					alert("선택한 행이 없습니다.");
				}else{
					let selectedList = _modal.tableManager.getCheckedRowsData();
					let data = $this.searchManager.getAddData(selectedList);
					$this.searchManager.addCallback(data);
				}
			},
			addCallback : function(data){
				$.ccs.table.addRow($this.tableManager.table.$CAT_TABLE, data);
				$.ccs.modal.close("searchCatModal");
			}
		}	
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>