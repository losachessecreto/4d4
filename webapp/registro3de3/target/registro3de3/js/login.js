var Login = {  
		
    authenticate: function (username, password) {
    	
    	if(!!username && !!password) {
    		
	    	Common.makeAPICall({'user': username, 'password': password}, 'login', 'POST', Login.loginResponseCompleted, null, Login.loginResponseCompleted);
    	}
    },
    loginResponseCompleted: function(data) {
    	if(!!data && data.success) {
    		document.location.href = '../declaraciones/index.html';
    	}
    	else if(!!data && !!data.responseJSON && !!data.responseJSON.message) {
    		$('#loginMessage').text(data.responseJSON.message);
    		$('#loginMessageDiv').show();
    	}
    }    
};
