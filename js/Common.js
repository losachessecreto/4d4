var Common = {
	apiUrl:'http://localhost:8080/registro3de3/webapi',    
	makeAPICall : function (data, moduleUrl, method, successCallback, successParams, errorCallback, errorParams) {
		
		var url =Common.apiUrl + '/' + moduleUrl;
		
    	$.ajax({    	
            method: method,
			url: url,
            data: data,
            error: function (data) {
	            debugger;
	            
            	alert('completed with error');            	
            	if(!!errorCallback) {
	            	errorCallback(data, errorParams);
            	}            	
            },
            success: function (data) {
	        	debugger;
	        	
                alert('success');    
            	if(!!successCallback) {            		
	                successCallback(data, successParams);
            	}
            }
        });
    }
};
