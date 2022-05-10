
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

})(jQuery);

