var Common = {
	apiUrl:'http://localhost:8080/registro3de3/webapi',    
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
$(document).ready(function() {

	$('#adminsSection li').click(function() {
		var selection = $(this).children('a').text();
		$('#selectedAdmin').text(selection);
		
		var selectedRoleId = $(this).data('role-id');
		
		$.each($('[name="filtrable"]'), function(index, element) {
			var elementRolesId = $(element).data('roles') + '';
			console.log('elementRolesId: "' + elementRolesId + '", selectedRoleId: ' +selectedRoleId);
			if(elementRolesId.indexOf(selectedRoleId) < 0) {
				$(element).hide();
			}
			else {
				$(element).show();
			}
		});
	});
});