var Common = {
	apiUrl:'http://192.168.15.11:8080/registro3de3/webapi',    
	makeAPICall : function (data, moduleUrl, method, successCallback, successParams, errorCallback, errorParams) {
		
		var url =Common.apiUrl + '/' + moduleUrl;
		
    	$.ajax({    	
            method: method,
			url: url,
            data: data,
            error: function (data) {
	            
	            console.log('error');
            	if(!!errorCallback) {
	            	errorCallback(data, errorParams);
            	}            	
            },
            success: function (data) {
	        	
            	if(!!successCallback) {            		
	                successCallback(data, successParams);
            	}
            }
        });
    }
};
