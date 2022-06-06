
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
                		originalCallbackFunction(_data['data'], responseStatus, jqXHR);
           		 	}	
				}else if (status == "102"){
					alert(_data['data']['msg']);
				}else if (status == "103"){
					alert(_data['data']['msg']);
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
	

			
			function fn_getFileInputLength(){
	            let $form;
	            if(parameterOptions.fileform){
	                $form = $(parameterOptions.fileform);
	                
	                return $("input[type='file']",$form).filter(function (){return this.value;}).length;
	                
	                let fileInputs = $('input[type=file]:enabled[value!=""]', $form);
	                console.log('call fileInputs', fileInputs);
	                return fileInputs.length;
	            }
	            return 0;
	        };

	 		let hasFileInputs = fn_getFileInputLength() > 0;
	        
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
						if($.ccs.modal.modalOption.closeFunction) this.modalOption.closeFunction();
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
		bindFile : function(selecterName, files){
			$("#" + selecterName + "-fileList").empty();
			$("#" + selecterName + "-fileInput").MultiFile('reset');
	        $("#" + selecterName + "-fileInput").MultiFile('addList',files);
		},
		findObjectByKey : function(list, key, name){
			return list.filter(function(e, idx){if(e[key] == name) return e;});
		},
		findIndexByKey : function(list, key, name){
			let result = null;
			list.filter(function(e, idx){if(e[key] == name) result = idx;});
			return result;
		},
		pluck : function(list, key){
			let result = [];
			list.each(function(e, idx){result.push(e[key])});
			return result;
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
				let arr = this.serializeArray();
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
							: $(b).parent().children("label") ? $(b).parent().children("label").html() + "은(는) " 
							: "";
            var d = this.findDefined(this.customMessage(b.name, c.method), this.customDataMessage(b, c.method), !this.settings.ignoreTitle && b.title || void 0, label + $.validator.messages[c.method], "<strong>Warning: No message defined for " + b.name + "</strong>")
              , e = /\$?\{(\d+)\}/g;


            return "function" == typeof d ? d = d.call(this, c.parameters, b) : e.test(d) && (d = a.validator.format(d.replace(e, "{$1}"), c.parameters)),
            d
        } 
	

})(jQuery);

