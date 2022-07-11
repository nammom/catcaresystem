<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
<body>
	<div class="container">
		<c:url value="/login" var="loginUrl" />
		<div class="col-md-12">
			<div class="col-md-6">
				<form class="form-group contents" id="sample-fileForm" method="post"
					enctype="multipart/form-data" accept-charset="UTF-8">
					<input type="hidden" id="file_grp_id" name="file_grp_id" />
					<div class="form-group">
						<label for="regDt" class="form-label mt-4">등록일자</label> 
						<input type="text" 
							id="regDt" name="regDt"
							class="form-control datePicker-control" 
							required readonly>
					</div>
					<div class="form-group">
						<label class="form-label mt-4">기간1</label> 
						<input type="text"
							id="startDate" name="startDate"
							class="form-control datePicker-control startDate" 
							required readonly> 
						<span>~</span> 
						<input type="text" 
							id="endDate" name="endDate" 
							class="form-control datePicker-control endDate"
							required readonly>
					</div>
					<div class="form-group">
						<label class="form-label mt-4">기간2</label> 
						<input type="text"
							id="startDate2" name="startDate2"
							class="form-control datePicker-control startDate" 
							readonly>
						<span>~</span> 
						<input type="text" 
							id="endDate2" name="endDate2"
							class="form-control datePicker-control endDate" 
							readonly>
					</div>
					<div class="form-group">
						<label for="title" class="form-label mt-4">제목</label> 
						<input type="text" 
						id="title" name="title"
						 class="form-control" />
					</div>
					<div class="form-group">
						<label for="contents" class="form-label mt-4">내용</label>
						<textarea id="contents" name="contents" class="form-control"></textarea>
					</div>
					<div class="form-group">
						<label for="contents" class="form-label mt-4">checkbox</label>
						<div class="form-check">
							<input type="checkbox" 
								name="color" value="blue"
								class='form-check-input'> 
							<label class='form-check-label'>Blue</label>
						</div>
						<div class="form-check">
							<input type="checkbox" 
								name="color" value="red"
								class='form-check-input'> 
							<label class='form-check-label'>Red</label>
						</div>
					</div>
					<div class="form-group">
						<label for="contents" class="form-label mt-4">checkbox2</label>
						<ccs:option type="checkbox" code="200" selectedValue="21"
							includeAll="전체" />
					</div>
					<div class="form-group">
						<label for="contents" class="form-label mt-4">select</label> 
						<select id="select" name="select" class="form-control">
							<ccs:option type="option" code="100" selectedValue="02" includeAll="전체" />
						</select>
					</div>
					<div class="form-group">
						<div id="sample-fileList" class="sta-multifile-upload-list"></div>
					</div>
					<div class="form-group">
						<span id="sample-addFileBtn"
							class="sta-fileinput-btn sta-fileinput-multi-btn sta-fileinput-btn-add fileinput-button">
							<span>파일선택</span> 
							<input type="file" 
								id="sample-fileInput"
								class="file-upload" />
						</span>
					</div>

					<button type="button" id="btn-save" class="btn btn-warning">저장</button>
				</form>
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

			this.initialize = function() {
				$this.initData();

				$("#btn-save").click(function() {
					$this.formManager.save();
				});

				// 첨부파일 추가 이벤트
				$("#sample-fileInput").MultiFile({
					accept : "pdf|jpg|jpeg|gif|png|bmp|zip|txt|xlsx|hwp|doc|docx|csv|",
					max : 20,
					list : "#sample-fileList", // upload / or selected files
					onFileSelect : function(element, value,
							master_element) {
						console.log(master_element);
					}
				});

			}

			this.initData = function() {
				let formId = "${formId}";
				if (formId) {
					$this.formManager.getData(formId);
				}
			}

			this.formManager = {
				getData : function(formId) {
					$.ccs.ajax({
						url : "/sample/fileform/selectData",
						data : {
							"formId" : formId
						},
						success : function(data) {
							$this.formManager.setData(data);
						}
					});
				},
				setData : function(data) {
					//form 초기화
					$("#sample-fileForm")[0].reset();
					//form 정보
					$("#sample-fileForm").bindJson(data['form']);
					// 첨부파일 정보
					if (data['files']) {
						$.ccs.bindFile("sample", data['files']);
					}
				},
				validate : function() {
					//error 메세지 변경 등 .. 옵션 변경 가능 
					/*
					$("#sample-fileForm").validate(
							{ 
								rules: { 
									title : {
										required: true
									} , 
									contents: {
										required: true,
										rangelength: [2, 10]} 
									}, 
								messages: { 
									title: { 
										required: "값을 입력해 주십시오." 
									}, 
									contents: { 
										required: "내용을 입력해 주십시오." ,
										rangelength: "내용은 최소 2자 최대 10자 이내로 입력해 주십시오." 
									} 
								} 
							}
						);

					 */

					return $("#sample-fileForm").valid();
				},
				save : function() {
					if ($this.formManager.validate()) {
						$.ccs.ajax({
							url : "/sample/fileform/save",
							fileform : '#sample-fileForm',
							data : this.getSaveData(),
							success : function(data) {
								alert("저장되었습니다.");
								let formId = data['formId'];
								location.replace("/sample/fileform/" + formId);
							}
						});
					}
				},
				getSaveData : function() {
					let jsonData = $("#sample-fileForm").serializeObject();
					// 기존에 있던 파일이 삭제 되었을 경우의 정보 담기.
					jsonData['deleteFiles'] = $("#sample-fileInput").MultiFile("toDeletedList");
					return jsonData;
				}
			}

		}
	</script>

	<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>