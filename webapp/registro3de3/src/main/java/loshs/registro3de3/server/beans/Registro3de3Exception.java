/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loshs.registro3de3.server.beans;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hugo
 */
@XmlRootElement
public class Registro3de3Exception extends WebApplicationException{
    
    public Registro3de3Exception() {
        super();
    }

    public Registro3de3Exception(Response response) {
        super(response);
    }
   

}
