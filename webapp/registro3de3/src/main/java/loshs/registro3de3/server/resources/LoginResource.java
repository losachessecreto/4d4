package loshs.registro3de3.server.resources;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import loshs.registro3de3.server.beans.DatasourceContainer;
import loshs.registro3de3.server.beans.HTTPJsonResponseObject;
import loshs.registro3de3.server.beans.PasswordHasher;
import loshs.registro3de3.server.beans.User;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        URI uri;
        if (authenticate(user.getUser(), user.getPassword().toCharArray())) {
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
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String user, @FormParam("password") String password) {
        URI uri;
        if (authenticate(user, password.toCharArray())) {
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
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getPostgresDatasource().getConnection();
            st = conn.prepareStatement("SELECT password FROM users WHERE \"user\" = ?");
            st.setString(1, username);
            rs = st.executeQuery();
            if (rs.next()) {
                String hashedPass =  rs.getString("password");
                psh.compare(password, hashedPass);
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }

        return username.equals(new String(password));
    }

    /*
    @GET
    @Path("hashpass")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hashpass(@QueryParam("pass") String pass)
            throws Exception {
        return Response.ok(psh.getSecureHash(pass.toCharArray())).build();
    }
    */
}
