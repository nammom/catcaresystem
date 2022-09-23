<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
<div class="container">
	<div class="col-md-12">
		<div class="col-md-10">
			<form class="form-group contents" id="habitat-form" method="post"
				enctype="multipart/form-data" accept-charset="UTF-8">
				<input type="hidden" id="s_user_cd" name="s_user_cd" value="<c:out value="${_SESSION_USER_CD_}" />"/>
				<input type="hidden" id="user_cd" name="user_cd" value="<c:out value="${user_cd}" />"/>
				<input type="hidden" id="file_grp_id" name="file_grp_id" />
				<!-- 위도 -->
				<input type="hidden" id="lat" name="lat" value="<c:out value="${lat}" />"/>
				<!-- 경도 -->
				<input type="hidden" id="lng" name="lng" value="<c:out value="${lng}" />"/>
		
		    	<div class="form-group">
					<label for="reg_dt" class="form-label mt-4">등록일자</label> 
					<input type="text" 
						id="reg_dt" name="reg_dt"
						class="form-control datePicker-control" 
						required disabled>
				</div>
			    <div class="form-group">
		        	<label for="habitat_cd" class="form-label mt-4">서식지 고유코드</label>
			        <input type="text" 
			        	id="habitat_cd" name="habitat_cd" 
			        	value="<c:out value="${habitat_cd}" />"
			        	class="form-control" 
			        	disabled/>
		    	</div>
		    	<div class="form-group">
		        	<label for="habitat_nm" class="form-label mt-4">서식지 명</label>
			        <input type="text" 
			        	id="habitat_nm" name="habitat_nm" 
			        	value="<c:out value="${habitat_nm}" />"
			        	class="form-control"
			        	required
			        	maxlength="60"
			        	<c:if test="${not empty habitat_cd}">disabled</c:if>/>
		    	</div>
		    	
	    		<div class="row">
					<div class="form-group col-md-1">
						<label for="sido" class="form-label">지역</label>
					</div>
					<div class="form-group col-md-3">
						<select id="sido_cd" name="sido_cd" data-code="sido" class="form-control area-select" caption="지역" required disabled>
		                	<ccs:area code="sido" includeAll="전체" />
		                </select>
					</div>
					<div class="form-group col-md-3">
						<select id="sigungu_cd" name="sigungu_cd" data-code="sigungu" class="form-control area-select" caption="지역" required disabled>
		                	<ccs:area code="sigungu" includeAll="전체" />
		                </select>
					</div>
					<div class="form-group col-md-3">
						<select id="dong_cd" name="dong_cd" data-code="dong" class="form-control area-select" caption="지역" required disabled>
		                	<ccs:area code="dong" includeAll="전체" /> 
		                </select> 
					</div>
					<div class="form-group col-md-2">
						<button type="button" id="btn-search-address" class="btn btn-primary btn-sm">검색</button>
					</div>
				</div>
		    	<div class="form-group">
		        	<label for="address" class="form-label mt-4">상세주소</label>
			        <input type="text" 
			        	id="address" name="address" 
			        	value="<c:out value="${address}" />"
			        	class="form-control"
			        	maxlength="60" 
			        	<c:if test="${not empty habitat_cd}">disabled</c:if>/>
		    	</div>
				<div class="form-group">
					<label class="form-label mt-4">서식지 사진</label>
					<div id="habitat-fileList" class="sta-multifile-upload-list"></div>
				</div>
				<div class="form-group">
					<span id="habitat-addFileBtn"
						class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
						<span>파일선택</span> 
						<input type="file" 
							id="habitat-fileInput"
							class="file-upload" 
							/>
					</span>
				</div>
			</form>
			<button type="button" id="btn-list" class="btn btn-warning">목록</button>
			<c:if test="${not empty habitat_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">수정</button>
		    	<button type="button" id="btn-delete" class="btn btn-warning">삭제</button>
		    </c:if>
		    <c:if test="${empty habitat_cd}">
		    	<button type="button" id="btn-save" class="btn btn-warning">저장</button>
		    </c:if>
		</div>
	</div>
</div>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
let _mypage = null;

$(document).ready(function() {

	_mypage = new fn_page();
	_mypage.initialize();
});

function fn_page() {
	let $this = this;
	let PAGE_URL = "/manage/habitat/form";
	let HABITAT_CD, S_USER_CD, USER_CD;
	
	this.initialize = function() {
		
		$this.initData();
		
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
		
		$("#btn-search-address").click(function(){
	        $this.mapManager.searchAddress();
		});
		
		// 첨부파일 추가 이벤트
		$("#habitat-fileInput").MultiFile({
			accept : "jpg|jpeg|gif|png",
			max : 1,
			list : "#habitat-fileList", // upload / or selected files
			onFileSelect : function(element, value, master_element) {
				console.log(master_element);
			}
		});
	}
	
	this.initData = function() {
		HABITAT_CD = $("#habitat_cd").val()? Number($("#habitat_cd").val()) : "";
		S_USER_CD = $("#s_user_cd").val();
		
		//등록일자를 오늘 일자로 설정
		let regDatepicker = $("#reg_dt").datepicker().data("datepicker");
		regDatepicker.selectDate(new Date());
		
		if(HABITAT_CD){
			$this.formManager.getData();			
		}
	}
	
	this.formManager = {
		getData : function(){
			$.ccs.ajax({
				url : PAGE_URL + "/selectData"
				, data : {"habitat_cd" : HABITAT_CD}
				, success : function(data){
					$this.formManager.setData(data);
				}
			});
		},
		setData : function(data){
			//form 초기화
			$("#habitat-form")[0].reset();
			//form 정보
			$("#habitat-form").bindJson(data);
			// 첨부파일 정보
			if (data['fileList']) {
				$.ccs.file.bindFile("habitat", data['fileList']);
			}
			//USER_CD set
			USER_CD = $("#user_cd").val();

			if(S_USER_CD == USER_CD){
				$("#address").attr("disabled", false);
				$("#habitat_nm").attr("disabled", false);
			}else{
				$("#btn-save").hide();
				$("#btn-delete").hide();
				$("#btn-search-address").hide();
				$("#habitat-addFileBtn").hide();
			}
		},
		setAddress : function(data) {
			$("#sido_cd").val("");
			$.each($("#sido_cd").children(), function(i, e){
				if(e.text == data.region_1depth_name) {
					$(e).prop("selected", true);
				}
			});
			
			$("#sigungu_cd").val("");
			$.each($("#sigungu_cd").children(), function(i, e){
				if(e.text == data.region_2depth_name) {
					$(e).prop("selected", true);
				}
			});
			
			$("#dong_cd").val("");
			$.each($("#dong_cd").children(), function(i, e){
				if(e.text == data.region_3depth_h_name) {
					$(e).prop("selected", true);
				}
			});
		},
		validate : function(data){
			if(!$("#habitat-form").valid()){
				 return false;
			}
			if(!$.ccs.file.validate("habitat", 1)){
				alert("서식지 사진은 필수입니다.");
				return false;
			}
			return true;
		},
		save : function() {
			let _data = this.getSaveData();
			if($this.formManager.validate(_data)){
				$.ccs.ajax({
					url : PAGE_URL + "/save"
					, fileform : '#habitat-form'
					, data : _data
					, success : function(data){
						alert("저장되었습니다.");
						$this.locationManager.list();	
					}
				});
			}
		},
		getSaveData : function() {
			let jsonData = $("#habitat-form").serializeObject();
			if(jsonData['habitat_cd']){
				jsonData['habitat_cd'] = Number(jsonData['habitat_cd']);
			}
			// 기존에 있던 파일이 삭제 되었을 경우의 정보 담기.
			jsonData['deleteFiles'] = $("#habitat-fileInput").MultiFile("toDeletedList");
			return jsonData;
		},
		deleteData : function() {
			$.ccs.ajax({
				url : PAGE_URL + "/delete"
				, data : {habitat_cd : HABITAT_CD}
				, success : function(data){
					alert("삭제되었습니다.");
					$this.locationManager.list();			
				}
			});
		}
	}
	
	this.mapManager = {
		address : "",
		searchAddress : function() {
			//카카오 지도 호출
	        new daum.Postcode({
	            oncomplete: function(data) { //선택시 입력값 세팅
	                console.log(data.address);
	            	$this.mapManager.address = data.address;
	                $this.mapManager.getAddressInfo();
	            }
	        }).open();
		},
		getAddressInfo : function() {
			//주소 상세 정보 호출
			 let options = {
					type : 'GET'
					, url : "https://dapi.kakao.com/v2/local/search/address.json?analyze_type=similar&page=1&size=10&query=" + $this.mapManager.address
					, dataType: 'JSON'
					, beforeSend: function (xhr) {
                        xhr.setRequestHeader("Authorization","KakaoAK e61c5306cffa0d2a7dab56b2d400f0fc");
                    }
                    , success: function (data) {
                        console.log(data);
                        //위도 경도 set
                        let addressInfo = data["documents"][0];
                        $("#lat").val(addressInfo.x);
                        $("#lng").val(addressInfo.y);
                        //주소 set
                        console.log(addressInfo.address);
                        $this.formManager.setAddress(addressInfo.address);
                    }
                }
			 
			 $.ajax(options);
            
		}
	}
	
	this.locationManager = {
		list : function() {
			location.href = "/manage/habitat?pagenm=habitat";		
			
		}
	}
}

</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>