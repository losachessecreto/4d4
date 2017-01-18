var Contralor = {  
	
	tableId: '#tableUsuarios',
	getUsuarios : function () {
		
    	Common.makeAPICall(null, 'users', 'GET', Contralor.getUsuariosCompleted, null, Contralor.loadTable);
    },
    getUsuario: function (usuarioId) {
    	if(!!usuarioId) {
    		
	    	Common.makeAPICall(null, 'users/' + usuarioId, 'GET', Contralor.getUsuarioCompleted);
    	}
    },
    getUsuarioCompleted: function(data) {
    	
    	if(!!data) {
    		var tableUser = Contralor.parseApiUsuario(data);
    		
    		$('#nombreUsuario').text(tableUser.nombre);    		
    		$('#correoUsuario').text(tableUser.correo);    		    		
    		$('#cargoUsuario').text(tableUser.cargo);    		
    		$('#entidadUsuario').text(tableUser.entidad);    		
    		$('#ciudadUsuario').text(tableUser.ciudad);    		    		
    		$('#rfcUsuario').text(tableUser.rfc);
    	}
    },    
    getUsuariosCompleted : function (data) {
    	
    	if(!!data) {
    		$.each(data, function (index, usuario){
    			
    			var tableUser = Contralor.parseApiUsuario(usuario);
    			$(Contralor.tableId + ' tbody').prepend(  '<tr>'
    													+ '<td>' + tableUser.nombre + '</td>'
    													+ '<td>' + tableUser.correo + '</td>'
    													+ '<td>' + tableUser.cargo + '</td>' 
    													+ '<td>' + tableUser.entidad + '</td>'
    													+ '<td>' + tableUser.ciudad + '</td>'
    													+ '<td>' + tableUser.username + '</td>'
    													+ '<td>' + tableUser.rfc + '</td>'
    													+ '<td><span class="glyphicon glyphicon-ok success pull-right"></span></td>'
    													+ '<td><div class="btn-group pull-right" role="group"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-cog"></span></button><ul class="dropdown-menu"><li><a href="../usuario/index.html?userId='+ tableUser.id +'">Editar</a></li><li><a href="#">Ver reporte de usuario</a></li><li><a href="#">Impugnar</a></li><li><a href="#">Desincorporar por defunción</a></li><li><a href="#">Cambiar de entidad</a></li><li><a href="#">Dar de baja</a></li></ul></div></td>'
    													+ '</tr>');    			    			        	
    		});
    		
    	}
    	Contralor.loadTable();
    },
    parseApiUsuario: function(usuario) {
    	
    	if(usuario == null) return null;
    	
    	var tableUser = {
    				id: usuario.id,
    				nombre: usuario.given_name + ' ' + usuario.father_lastname + ' ' + usuario.mother_lastname,
    				correo: usuario.mail,
    				username: usuario.user,
    				cargo: usuario.position,
    				rfc: usuario.rfc,
    				entidad: usuario.entity,
    				ciudad: usuario.city
    			};
    			
    	return tableUser;	
    },
    loadTable: function() {
    	
    	$(Contralor.tableId).dataTable({
        	'scrollCollapse' : true,
            'scrollY': "250px"
         }); 
    }
};
