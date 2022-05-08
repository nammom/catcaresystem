
(function ($){
	
	let ccs = {
		ajax : function(options){
			let header = $("meta[name='_csrf_header']").attr('content');
			let token = $("meta[name='_csrf']").attr('content');
				
			let attachDefaultOptions = {
	            type: "POST" 
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
				let status = responseData['status'];
				if (status == "101"){
					if (originalCallbackFunction != null) {
                		originalCallbackFunction(responseData['data'], responseStatus, jqXHR);
           		 	}	
				}else if (status == "102"){
					alert(responseData['data']['msg']);
				}else if (stauts = "103"){
					alert(responseData['data']['msg']);
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
	        parameterOptions.error = errorCallbackFunction;
	
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

