package loshs.registro3de3.server.beans;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Registro3de3Exception extends WebApplicationException{
    
    public Registro3de3Exception() {
        super();
    }

    public Registro3de3Exception(Response response) {
        super(response);
    }
   

}
