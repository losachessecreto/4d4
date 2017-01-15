var Login = {  
		
    authenticate: function (username, password) {
    	
    	if(!!username && !!password) {
    		
	    	Common.makeAPICall({'user': username, 'password': password}, 'login', 'POST');
    	}
    }
};
