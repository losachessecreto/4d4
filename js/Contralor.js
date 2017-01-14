var Contralor = {  
	
	tableId: '#tableUsuarios',
	getUsuarios : function () {
		
    	Common.makeAPICall(null, 'users', 'GET', Contralor.getUsuariosCompleted);
    },
    getUsuariosCompleted : function (data) {
    	
    	if(!!data) {
    		$.each(data, function (index, usuario){
    			var tableUser = {
    				nombre: usuario.given_name + ' ' + usuario.father_lastname + ' ' + usuario.mother_lastname,
    				correo: usuario.mail,
    				username: usuario.user,
    				cargo: usuario.position,
    				rfc: usuario.rfc,
    				entidad: usuario.entity,
    				ciudad: usuario.city
    			};
    			
    			$(Contralor.tableId + ' tbody').prepend(  '<tr>'
    													+ '<td>' + tableUser.nombre + '</td>'
    													+ '<td>' + tableUser.correo + '</td>'
    													+ '<td>' + tableUser.cargo + '</td>' 
    													+ '<td>' + tableUser.entidad + '</td>'
    													+ '<td>' + tableUser.ciudad + '</td>'
    													+ '<td>' + tableUser.username + '</td>'
    													+ '<td>' + tableUser.rfc + '</td>'
    													+ '<td><span class="glyphicon glyphicon-ok success pull-right"></span></td>'
    													+ '<div class="btn-group pull-right" role="group"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog"></span></button><ul class="dropdown-menu"><li><a href="#">Editar</a></li><li><a href="#">Ver reporte de usuario</a></li><li><a href="#">Impugnar</a></li><li><a href="#">Desincorporar por defunción</a></li><li><a href="#">Eliminar</a></li></ul></div>'
    													+ '</tr>');    			    			        	
    		});
    		$(Contralor.tableId).dataTable({
         			'scrollCollapse' : true,
                	'scrollY': "250px"
         	}); 
    	}
    }
};
