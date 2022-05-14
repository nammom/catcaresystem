
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
				if(parameterOptions['contentType'] == "application/json; charset=utf-8" && parameterOptions['data']){
					parameterOptions['data'] = JSON.stringify({"___AJAX_DATA___" : parameterOptions['data']});
				}
				//parameterOptions['data'] = JSON.stringify(parameterOptions['data']);
			}
			return $.ajax(parameterOptions);
		},
		confirm : function(message, callbackFunc){
			let result = confirm(message);
			if(result){
				callbackFunc();
			}
		},
		bindFile : function(selecterName, files){
			$("#" + selecterName + "-fileList").empty();
			$("#" + selecterName + "-fileInput").MultiFile('reset');
	        $("#" + selecterName + "-fileInput").MultiFile('addList',files);
		}
	}
	
	
	$.extend({"ccs" : ccs});	
	
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
			
	
		
	

})(jQuery);

