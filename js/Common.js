var Common = {
	apiUrl:'http://192.168.15.18:8080/registro3de3/webapi',    
	makeAPICall : function (data, moduleUrl, method, successCallback, successParams, errorCallback, errorParams) {
		
		var url =Common.apiUrl + '/' + moduleUrl;
		$('.spinner').show();
    	$.ajax({    	
            method: method,
			url: url,
            data: data,
            error: function (data) {
	            
	            console.log('error');
            	if(!!errorCallback) {
	            	errorCallback(data, errorParams);
            	}            
            	$('.spinner').hide();	
            },
            success: function (data) {
	        	
            	if(!!successCallback) {            		
	                successCallback(data, successParams);
            	}
            	$('.spinner').hide();
            }
        });
    },
    getParameterByName: function (name, url) {
    	if (!url) {
      		url = window.location.href;
    	}
    	name = name.replace(/[\[\]]/g, "\\$&");
    	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    	if (!results) return null;
    	if (!results[2]) return '';
    	return decodeURIComponent(results[2].replace(/\+/g, " "));
	}
};
