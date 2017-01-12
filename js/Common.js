var Common = {
	apiUrl:'http://localhost:8080/registro3de3/webapi',    
	makeAPICall : function (data, moduleUrl, method, successCallback, successParams, errorCallback, errorParams) {
    	$.ajax({
			url: Common.apiUrl + '/' + moduleUrl,
            type: method,
            data: data,
            crossDomain: true,
            dataType: 'jsonp',
            error: function (xhr, status) {
            	alert('completed with error');
            	debugger;
            	if(!!errorCallback) {
	            	errorCallback(data, errorParams);
            	}            	
            },
            success: function (data) {
	            alert('success');
            	debugger;
            	if(!!successCallback) {            		
	                successCallback(data, successParams);
            	}
            }
        });
    }
};
