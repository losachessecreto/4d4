var Login = {  
		
    authenticate: function (username, password) {
    	
    	if(!!username && !!password) {
    		
	    	Common.makeAPICall({'user': username, 'password': password}, 'login', 'POST', Login.loginResponseCompleted, Login.loginResponseCompleted);
    	}
    },
    loginResponseCompleted: function(data) {
    	document.location.href = '../declaraciones/index.html';
    }    
};
