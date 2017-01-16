package loshs.registro3de3.server.resources;

import java.net.URI;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import loshs.registro3de3.server.beans.DatasourceContainer;
import loshs.registro3de3.server.beans.HTTPJsonResponseObject;
import loshs.registro3de3.server.beans.PasswordHasher;

@ManagedBean
@Path("login")
public class LoginResource {

    @Context
    DatasourceContainer dsc;
    
    @Context
    PasswordHasher psh;

    @Context
    HttpServletRequest request;

    static final Logger LOGGER = Logger.getLogger(LoginResource.class.getName());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String username, @FormParam("password") String password) {
        URI uri;
        if (authenticate(username, password.toCharArray())) {
            request.getSession(true);
            //return Response.seeOther(uri).cookie(new NewCookie("name", "Hello, world!")).build();
            return Response.status(Status.OK).entity(new HTTPJsonResponseObject(200, "OK",
                    "Autorizado", Boolean.TRUE)).build();

        } else {
            return Response.status(Status.UNAUTHORIZED).entity(new HTTPJsonResponseObject(401, "Unauthorized",
                    "El usuario o constraseña son incorrectos", Boolean.FALSE)).build();
            //return Response.seeOther(uri).cookie(new NewCookie("name", "Hello, world!")).build();
        }
    }

    public boolean authenticate(String username, char[] password) {
        return username.equals(new String(password));
    }

    @GET
    @Path("hashpass")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hashpass(@QueryParam("pass") String pass)
            throws Exception {
        return Response.ok(psh.getSecureHash(pass.toCharArray())).build();
    }

   
}
