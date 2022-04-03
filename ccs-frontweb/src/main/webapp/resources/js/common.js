
(function ($){
	
	let ccs = {
		ajax : function(options){
			let attachDefaultOptions = {
	            dataType: 'text'
	            , type: "POST" 
	            , success: null
				, error: null
	        };
	
	        let parameterOptions = $.extend({}, attachDefaultOptions, options);
	        
			//success
	        let originalCallbackFunction = parameterOptions.success;
	        let successCallbackFunction = function (responseData, responseStatus, jqXHR) {
	    		// check session
				
				 // bind success callback function
	            if (originalCallbackFunction != null) {
	                originalCallbackFunction(responseData, responseStatus, jqXHR);
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
		
})(jQuery);

