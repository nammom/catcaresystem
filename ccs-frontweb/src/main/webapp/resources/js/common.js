
(function ($){
	
	let ccs = {
		ajax : function(options){
			let header = $("meta[name='_csrf_header']").attr('content');
			let token = $("meta[name='_csrf']").attr('content');
				
			let attachDefaultOptions = {
	            type: "POST" 
				, data : null
				, dataType: 'JSON'
				, contentType : 'application/json; charset=utf-8'
	            , success: null
				, error: null
				, beforeSend: function(xhr){
				        xhr.setRequestHeader(header, token);
				    }
	        };
	
	        let parameterOptions = $.extend({}, attachDefaultOptions, options);
	        
			//success
	        let originalCallbackFunction = parameterOptions.success;
	        let successCallbackFunction = function (responseData, responseStatus, jqXHR) {
	    		// check session
				let _data = responseData;
				let status = _data['status'];
				if (status == "101"){
					if (originalCallbackFunction != null) {
                		originalCallbackFunction(_data['data'] || null, responseStatus, jqXHR);
           		 	}	
				}else if (status == "102"){
					if(_data['data']){
						alert(_data['data']['msg']);
					}
				}else if (status == "103"){
					if(_data['data']){
						alert(_data['data']['msg']);
					}
				}
	        };
	        parameterOptions.success = successCallbackFunction;
	
			//error
	        let originalErrorFunction = parameterOptions.error;
	        let errorCallbackFunction = function (error) {
	    		// check session
				
				 // bind success callback function
	            if (originalErrorFunction != null) {
	                originalErrorFunction(error);
	            }
	        };
	        parameterOptions['error'] = errorCallbackFunction;

			// 파일 여부 체크
			let hasFileInputs = false;
			if(parameterOptions.fileform){
		 		hasFileInputs = $.ccs.file.getFileInputLength(parameterOptions.fileform) > 0;
			}
	        if(hasFileInputs){
	        	let $form = $(parameterOptions.fileform);
				let _data = new FormData($form[0]);
				_data.append("___AJAX_DATA___", JSON.stringify(parameterOptions['data']));
				let attachFileOptions = {
					data : _data
					, enctype : 'multipart/form-data'
					, contentType : false
		            , processData: false
					, beforeSubmit: function(data, $form, option){
						//console.log($form);
						return true;
					}
		        };
	
	        	parameterOptions = $.extend({}, parameterOptions, attachFileOptions);
	        }else{
				parameterOptions['data'] = JSON.stringify(parameterOptions['data']);
			}
			return $.ajax(parameterOptions);
		},
		confirm : function(message, callbackFunc){
			let result = confirm(message);
			if(result){
				callbackFunc();
			}
		},
		file : {
			/**
				파일 갯수
			 */
			getFileInputLength : function fn_getFileInputLength(fileformId){
				
	            let $form;
	            if(fileformId){
	                $form = $(fileformId);
	                return $("input[type='file']",$form).filter(function (){return this.value;}).length;
	            }
	            return 0;
	        },
			/**
				파일 데이터 바인딩
			 */
			bindFile : function(selecterName, files){
				$("#" + selecterName + "-fileList").empty();
				$("#" + selecterName + "-fileInput").MultiFile('reset');
		        $("#" + selecterName + "-fileInput").MultiFile('addList',files);
			},
			/**
				파일 미리보기 이미지 경로 바인딩
			 */
			bindPreview : function(filePath, previewId){
				if (!previewId) previewId = "preview";
				document.getElementById(previewId).src = "/images/" + filePath;
			},
			/**
				파일 여부 체크
				@param fileId
				@param minCount
			 */
			validate : function(fileId, minCount){
				if($("#" + fileId + "-fileList").children().length < minCount) {
					return false;
				}
				return true;
			},
			/**
				파일 초기화
			 */
			reset : function(fileId){
				$("#" + fileId + " input:file").MultiFile("reset");
			},
			/**
				파일 업로드시 미리보기 바인딩
			 */
			preview : function(file, previewId) {
				if (file) {
				    let reader = new FileReader();
					if (!previewId) previewId = "preview";
				    reader.onload = function(e) {
				      document.getElementById(previewId).src = e.target.result;
				    };
				    reader.readAsDataURL(file);
				} else {
				    document.getElementById(previewId).src = "";
			  	}
			}
			
		},
		modal : {
			modalOption : {},
			create : function(url, option){
				let defaultOption = {
					id : "ccsModal",
					title : "",
					button : [],
					size : "modal-xl",	//modal-xl, modal-l, modal-sm
					closeFunction : null
				};
				this.modalOption = {};
				this.modalOption =  $.extend({}, defaultOption, option);
				if($("#" + $.ccs.modal.modalOption.id).length == 0){
					//모달 div 생성
					let modalHtml = '<div class="modal" id=\"'+$.ccs.modal.modalOption.id+'\" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">'
									+ '<div class="modal-dialog '+$.ccs.modal.modalOption.size+' modal-dialog-centered" role="document">'
					    			+ '<div class="modal-content"><div class="modal-header">'
							        + '<h5 class="modal-title" id="exampleModalLongTitle">'+$.ccs.modal.modalOption.title+'</h5>'
							        + '<button type="button" class="close" data-dismiss="modal" ><span aria-hidden="true">&times;</span></button></div>'
							      	+ '<div class="modal-body"></div><div class="modal-footer"></div></div></div></div>'
					$(".container").after(modalHtml);
					
					//모달 body 생성
					$(".modal-body", "#" + $.ccs.modal.modalOption.id).load(url, function(){
						//버튼 생성
						if($.ccs.modal.modalOption.button){
							let btnHtml = "";
							$.ccs.modal.modalOption.button.forEach(function(e, idx){
								btnHtml += '<button type="button" class="btn btn-primary" id="'+e['id']+'">'+e['title']+'</button>';
							});
						    $(".modal-footer", "#" + $.ccs.modal.modalOption.id).html(btnHtml);
						}
						$.ccs.modal.show($.ccs.modal.modalOption.id);
					});
					
					//버튼 클릭 이벤트 바인딩 
					if($.ccs.modal.modalOption.button){
						$.ccs.modal.modalOption.button.forEach(function(e, idx){
							let el = "#" + e['id'];
							$("#" + $.ccs.modal.modalOption.id).on("click", el, e['click']);
						});
					}
					
					//모달 숨김 이벤트
					$("#" + $.ccs.modal.modalOption.id).on('hide.bs.modal', function(){
						if($.ccs.modal.modalOption.button){
							$.ccs.modal.modalOption.button.forEach(function(e, idx){
								let el = "#" + e['id'];
								$("#" + $.ccs.modal.modalOption.id).off("click", el);
							});
						}
						if($.ccs.modal.modalOption.closeFunction) $.ccs.modal.modalOption.closeFunction();
					});
	
					//모달 숨겨진 뒤 이벤트
					$("#" + $.ccs.modal.modalOption.id).on('hidden.bs.modal', function(){
					    $(this).closest(".modal").remove();
					});
					
					
				}else{
					$.ccs.modal.show($.ccs.modal.modalOption.id);
				}
			},
			show : function(id){
				$('#' + id).modal('show');
			},
			close : function(id){
				let $modal;
				if(id){
					$modal = "#" + id;
				}else{
					$modal = "#" +$(this).closest(".modal").attr("id");
				}
				$(".close", $modal).click();
			}
		},
		/**
			listMap에서 key가 name인 map 반환
			@return list
		 */
		findObjectByKey : function(list, key, name){
			if(list && list.length){
				return list.filter(function(e, idx){if(e[key] == name) return e;});
			}
			return [];
		},
		/**
			listMap에서 key가 name인 map의 인덱스 반환
			@return list
		 */
		findIndexByKey : function(list, key, name){
			let result = null;
			if(list && list.length){
				list.filter(function(e, idx){if(e[key] == name) result = idx;});
			}
			return result;
		},
		/**
			listMap의 map[key] list를 반환
			@return list
		 */
		pluck : function(list, key){
			let result = [];
			if(list && list.length){
				list.forEach(function(e, idx){result.push(e[key])});
			}
			return result;
		},
		/**
			상하계층 구조 공통코드 select option 생성
		 */
		cmnCodeOption : {
			/**
				공통 코드 select option 생성
				@param obj : 타겟 컴포넌트 or 선택자 텍스트 
			 */
			getCmnCodeList : function(obj){
				$.ccs.cmnCodeOption.getCodeList(obj, "/selectCmnCode", "tree-select");
			},
			/**
				지역 코드 select option 생성
				@param obj : 타겟 컴포넌트 or 선택자 텍스트 
			 */
			getAreaCodeList :  function(obj){
				$.ccs.cmnCodeOption.getCodeList(obj, "/selectAreaCode", "area-select");
			},
			/**
				공통 코드 조회 후 옵션 생성
				@param obj : 타겟 컴포넌트 or 선택자 텍스트 
				@param url : 코드 조회 ajax url
				@param className : "tree-select" or "area-select"
			 */
			getCodeList : function(obj, url, className) {
				let length = $("." + className).length;
				let idx = $("." + className).index($(obj));

				$.ccs.cmnCodeOption.selectCodeList(url, className, idx, length);
			},
			/**
				공통 코드 서버조회
				@param url : 코드 조회 ajax url
				@param className : "tree-select" or "area-select"
				@param idx : 컴포넌트그룹 중 타겟 컴포넌트 순서
				@param length : 컴포넌트그룹의 사이즈
			 */
			selectCodeList : function(url, className, idx, length) {
				let $selectedOption = $("." + className + ":eq(" + (idx) + ") option:selected");
				let $targetSelect = $("." + className).eq(++idx);
				let _code = $targetSelect.data("code");
				let _prntCode = $selectedOption.val();
				
				//부모 select 의 value가 있는 경우 (= "전체" 가 아닌 경우)
				if(_prntCode) {
					let _data = {
									"code" : _code
									, "prntCode" : _prntCode
								};
								
					$.ccs.ajax({
						url : url
						, data : _data
						, success : function(data){
							$.ccs.cmnCodeOption.createOption($targetSelect, data["codeList"]);
							
							//하위 select 가 있는 경우 
							if(!$targetSelect.hasClass("last-select")){
								//하위 select 코드 조회 
								$.ccs.cmnCodeOption.selectCodeList(url, className, idx, length);
							}
						}
					});
				} else {
					//부모 select 의 value가 있는 경우 (= "전체" 인 경우)
					while(length > idx){
						$targetSelect = $("." + className).eq(idx++);
						$.ccs.cmnCodeOption.createOption($targetSelect, []);
						//마지막 select 인  경우 종료
						if($targetSelect.hasClass("last-select")) break;	
					}
				}
			},
			/**
				옵션생성
				@param targetSelect : select 컴포넌트
				@param codeList : 코드리스트
			 */
			createOption : function(targetSelect, codeList) {
				let optionHtml = "";
				let firstOption = targetSelect.children().first();
				let selectedOption = targetSelect.filter(':selected');
				if(!firstOption.val() && firstOption.html()){
					optionHtml += "<option value=\"\">" + (firstOption.html() || "전체")  + "</option>";
				}
				optionHtml += this.createOptionHtml(codeList, selectedOption.val());
				targetSelect.html(optionHtml);
			},
			/**
				옵션 html 생성
				@param codeList : 코드리스트
				@param selectValue : 선택 값
			 */
			createOptionHtml : function(codeList, selectValue) {
				let optionHtml = "";
				codeList.forEach(function(e, idx){
					if(selectValue == e['value']){
						optionHtml += "<option value=\"" + e['value'] + "\" data=prnt=\"" + e['prntcode'] + "\" selected>" + e['name'] + "</option>";
					}else{
						optionHtml += "<option value=\"" + e['value'] + "\" data=prnt=\"" + e['prntcode'] + "\">" + e['name'] + "</option>";
					}
				});
				return optionHtml;
			},
			/**
				하위계층 select 옵션 초기화
				@param idx : tree-select or area-select 컴포넌트그룹 중 타겟 컴포넌트 순서
				@param className : "tree-select" or "area-select"
			 */
			initSelect : function(idx, className){
				$.each($("." + className), function(i, e){
					if(i > idx){
						
						let optionHtml = "";
						let firstOption = $(e).children().first();
						if(!firstOption.val() && firstOption.html()){
							optionHtml += "<option value=\"\">" + (firstOption.html() || "전체") + "</option>";
						}
						$(e).html(optionHtml);
						//마지막  select 인  경우 종료
						if($(e).hasClass("last-select")) return false;
					}
				})
			}
		},
		/**
			datatables 메서드 
		 */
		table : {
			remove : function(table) {	//datatables 제거 
				table.destroy();
				let tableId = table.table().node().id;
				$('#' + tableId).empty();
			},
			getSelectedRowsObj : function(table) {	//선택한 행 객체 가져오기
				return table.rows('.selected');
			},
			getSelectedRows : function(table) {	//선택한 행 데이터 가져오기
				return table.rows('.selected').data().toArray();
			},
			clearData : function(table){	//테이블  데이터 초기화
				table.rows().data().clear().draw();
			},
			deleteRow : function(table){	//행삭제
				let rowsArr = this.getCheckedRows(table);
				if(!rowsArr.length){
					alert("선택한 행이 없습니다.");
				}else{
					let deleteRows = this.getCheckedRows(table);
					table.rows(deleteRows).remove().draw();
				}
			},
			addRow : function(table, data){	//행추가
				table.rows.add(data).draw();
			},
			getAllRows : function(table) {	//전체 행 가져오기
				//테이블 전체 행 td 데이터
				let rowsDataArr = table.rows().data().toArray();
				//테이블 전체 행 input, select value 데이터
				let inputRowsDataArr = table.$("input, select").serializeArray();
			 	if(inputRowsDataArr && inputRowsDataArr.length){
					let resultArr = [];
					//input, select 있는 경우
					let rowCnt = table.rows().data().length;
					let divideCount = inputRowsDataArr.length / rowCnt;
					inputRowsDataArr = inputRowsDataArr.division(divideCount);
			
					$.each(inputRowsDataArr, function(i, e) { 
						let targetArr = this;
						let obj = {};
						$.each(targetArr, function() { 
							obj[this.name] = this.value; 
						});
						//td 데이터와 input, select 데이터 합치기
						let resultObj = $.extend({}, rowsDataArr[i], obj);
						resultArr.push(resultObj);
					}); 
					return resultArr;
				}else{
					//input, select 없는 경우
					return rowsDataArr;
				}
				
			},
			getCheckedRows : function(table) {	//체크박스 체크된 행 가져오기
				let rowsArr = []; 
				let tableId = table.table().node().id;
				$.each($("input[name='input-check']:checked", "#" + tableId),function(i,e){
					rowsArr.push($(e).closest('tr').index());
				});
				return rowsArr;
			},
			getCheckedRowsData : function(table) {	//체크박스 체크된 행 데이터 가져오기
				let arr = this.getCheckedRows(table);
				if(arr.length > 0){
					return table.rows(arr).data().toArray();
				};
				return null;
			},
			reloadData : function(table) {	//데이터 검색
				table.ajax.reload();
			}
		},
		/**
			네비게이션 메뉴
		 */
		menu : {
			menuCount : 3	
		}
		
		
		
	}
	
	
	$.extend({"ccs" : ccs});	
	
	/*
		form array data convert to object(json)
	 */
	$.fn.serializeObject = function() { 
		let obj = null;
		try { 
			if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) {
				let disableds = $(this).find(':disabled').removeAttr('disabled');
				let arr = this.serializeArray();
				disableds.attr('disabled', 'disabled');
			 	if(arr){ 
					obj = {};
					$.each(arr, function() { 
						obj[this.name] = this.value; 
					}); 
				} 
			} 
		}catch(e) {
			 alert(e.message); 
		}finally {
			
		} return obj; 
	} 
	
	/*
		bind form data
	 */
	$.fn.bindJson = function(json){
		this[0].reset();
		let $form = $(this);
		
		for(let key in json){
			let value = json[key];
			if(key){
				let $targetElement = $("[name=" + key +"]", $form);
				if($targetElement.length == 1){
					if(typeof(value) !== "object"){
						switch($targetElement[0].nodeName){
							case "SELECT" : 
								$targetElement.val(value).prop("selected", true);
								break;
							case "INPUT" : 
								if($targetElement.attr("type") == "checkbox"){
									if(typeof(value) !== "object"){
										if($targetElement.val() == value){
											$targetElement.prop("checked", true);
										}
									}
								}else if($targetElement.attr("class") && $targetElement.attr("class").indexOf("datePicker-control") > 0){
									$targetElement.val(value);
									let datepicker = $targetElement.datepicker().data('datepicker');
									if(datepicker){
										datepicker.selectDate(new Date(value));
									}
								}else{
									$targetElement.val(value);
								}
								break;
							case "TEXTAREA" : 
								$targetElement.val(value);
								break;
						}
					}else{
						continue;
					}
				}else if($targetElement.length > 1){
					switch($targetElement[0].nodeName){
							case "INPUT" : 
								if($targetElement.attr("type") == "checkbox"){
										$targetElement.each(function(){
											let $ctl = $(this);
											if(typeof(value) !== "object"){
												if($ctl.val() == value){
													$ctl.prop("checked", true);
												}
											}else{
												if(value.length > 0){
													value.forEach(function(i){
														if($ctl.val() == i){
															$ctl.prop("checked", true);
														}
													});
												}
											}
										});
									}
									break;
								}
								
						}
				}
			}
		}
		
		/*
			validation form data
	 	*/	
		$.validator.setDefaults({
			onkeyup:false,
			onclick:false, 
			onfocusout:false, 
			showErrors:function(errorMap, errorList){
				 if(this.numberOfInvalids()) {
					 alert(errorList[0].message);
					 
				}
			}
		});

		//@validate plugin override
		$.validator.prototype.defaultMessage = function(b, c) {
			['validate defaultMessage override']
            "string" == typeof c && (c = {
                method: c
            });

			var label = b.labels[0] ? b.labels[0].innerHTML + "은(는) " 
							: $(b).parent().children("label").length > 0 ? $(b).parent().children("label").html() + "은(는) " 
							: $(b).attr("caption") && $(b).attr("caption").length > 0 ? $(b).attr("caption") + "은(는) "
							: "";
            var d = this.findDefined(this.customMessage(b.name, c.method), this.customDataMessage(b, c.method), !this.settings.ignoreTitle && b.title || void 0, label + $.validator.messages[c.method], "<strong>Warning: No message defined for " + b.name + "</strong>")
              , e = /\$?\{(\d+)\}/g;


            return "function" == typeof d ? d = d.call(this, c.parameters, b) : e.test(d) && (d = $.validator.format(d.replace(e, "{$1}"), c.parameters)),
            d
        } 

		//@validate plugin override
		$.validator.prototype.checkForm = function(b, c) {
			['validate checkForm 동일한 name array element 체크']
             this.prepareForm();
		    for (var i = 0, elements = (this.currentElements = this.elements()); elements[i]; i++) {
		        if (this.findByName(elements[i].name).length != undefined && this.findByName(elements[i].name).length > 1) {
		            for (var cnt = 0; cnt < this.findByName(elements[i].name).length; cnt++) {
		                this.check(this.findByName(elements[i].name)[cnt]);
		            }
		        } else {
		            this.check(elements[i]);
		        }
		    }
		    return this.valid();
        } 
	
		/**
			array를 count로 묶어 분할
		 */
		Array.prototype.division = function(count) {
		  let arr = JSON.parse(JSON.stringify(this));
		  let length = arr.length;
		  let divide = Math.floor(length / count) + (Math.floor( length % count ) > 0 ? 1 : 0);
		  let newArray = [];
		
		  for (let i = 0; i < divide; i++) {
		    // 배열 0부터 n개씩 잘라 새 배열에 넣기
		    newArray.push(arr.splice(0, count)); 
		  }
		
		  return newArray;
		}

	/** 
		이미 업로드 되어있는 파일 리스트 추가시 이미지 프리뷰 띄우게 수정
		filePath Option 추가
		삭제시 confirm 창 추가
	*/
	$.fn.MultiFile.addList = function(options,attachOption){
			var defaultOption = {
				fileName :'',
				fileExtension :'',
				fileSize : 0,
				url : '',
				fileId :null,
				filePath : ''
			};
			
			// 데이터 베이스에 따라서 속성명 변경
			function cvtUItoPlugn(dbOption){
				var uiOption = {
						fileName :dbOption['FILENAME'],
						fileExtension :dbOption['EXTCLASS'],
						fileSize : dbOption['FILESIZE'],
						url : dbOption["URL"],
						fileId :dbOption["FILEID"],
						filePath : dbOption["FILEPATH"]
					};
				return uiOption;
			}
			
			function sl(x) {
				return x > 1048576 ? (x / 1048576).toFixed(1) + 'Mb' : (x==1024?'1Mb': (x / 1024).toFixed(1) + 'Kb' )
			};
	
			/**
			 * set default attach options
			 */
			attachOption = $.extend(true,{
				onFileDeleted : function(json){
					
				},
				deleteMsg:'임시 삭제하였습니다.',
				canDelete : true
			},attachOption);
			
			
			//var newOptions = $.extend(true,defaultOption,options);
			var mf = this.MultiFile('data');
			if(!mf) return !console.log('MultiFile plugin not initialized');
			
			mf.oldList = [];
			if($.isPlainObject(options)){
				mf.oldList.push(cvtUItoPlugn(options));
			}
			else if($.isArray(options)){
				for(var i=0;i < options.length ; i++){
					mf.oldList.push(cvtUItoPlugn(options[i]));
				}
				
			} else{
				//alert('invalid type parameter');
				//return;
			}
			
			var $container = $(mf.inputOptions.list);
			//return;
			
			
			var prev_totla_size = 0;
			for(var i=0; i < mf.oldList.length ; i++){
				prev_totla_size += mf.oldList[i]['fileSize'] || 0;
			}
			for(var i=0; i < mf.deletedList.length ; i++){
				prev_totla_size -= mf.deletedList[i]['fileSize'] || 0;
			}
			mf.totla_size += prev_totla_size;
			var strTmpl = '<div data-file="{{:fileId}}" class="MultiFile-label">'+
							'<span>'+
								'<span  class="MultiFile-label" title="{{>fileName}}">' +
									"<div class='ico-file {{:extClass}}'></div>" +
									'<span class="MultiFile-title"><a href="{{:url}}">{{>fileName}}</a></span>'+
									'{{if fileSize > 0 }}<span style="padding-left:10px">({{g_kb:fileSize}})</span>{{/if}}'+
									'<img class="MultiFile-preview" style="max-height:100px; max-width:100px;" src="/images/{{:filePath}}">' +
								'</span>'+
							'</span>'+
							' <a data-file="{{:fileId}}" data-file-click="Y" class="MultiFile-remove" style="cursor:pointer">' + mf.STRING.remove + '</a>'+
						 '</div>';
			var myTmpl = $.templates(strTmpl);
			$.views.converters("g_kb", function(fs) {
			  var rtn = sl(fs);
			  //var rtn_1 =  $.format.number(rtn,'#,###,###');
			  return rtn;
			});
			for(var i=0;i < mf.oldList.length ;i++){
				var jsonFile = $.extend(true,defaultOption,mf.oldList[i]);
				jsonFile.extClass = $.fn.MultiFile.getFileCss(jsonFile.fileExtension);
				var html = myTmpl.render(jsonFile);
				$container.append(html);
			};
			if(attachOption.canDelete){
				$('[data-file-click]',$container).on('click',function(ev){
					var $this = $(this);
					function _deleteFile(){
						var fileId = $this.attr('data-file');
						//console.log(fileId);
						var files = $.grep(mf.oldList,function(element,index){
							return (fileId && element.fileId && (element.fileId == fileId));
						});
						
						$('div.MultiFile-label[data-file="' + fileId + '"]',$container).remove();
						
						for(var i=0; i < files.length ; i++){
							mf.deletedList.push(files[i]);
							if(attachOption.onFileDeleted){
								attachOption.onFileDeleted(files[i]);
							}							
						}
					};
					if(ev && ev.isTrigger == 3){
						_deleteFile();
					} else{
						/** 
							삭제 시 confirm 창 추가
						*/
						
						$.ccs.confirm(attachOption.deleteMsg || 'Do you want delete file?',function(isOK){
							//if(isOK){
								_deleteFile();
							//}
						});
					}
					
				});
			} else{
				$('[data-file-click]',$container).hide();
			}
			
		}
})(jQuery);

