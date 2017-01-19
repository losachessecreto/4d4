package loshs.registro3de3.server.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import loshs.registro3de3.server.beans.DatasourceContainer;
import loshs.registro3de3.server.beans.HTTPJsonResponseObject;
import loshs.registro3de3.server.beans.Registro3de3Exception;
import loshs.registro3de3.server.beans.User;

@ManagedBean
@Path("test/users")
public class UsersResourceNoauthTest {

    @Context
    DatasourceContainer dsc;

    static final Logger LOGGER = Logger.getLogger(UsersResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            LinkedList<User> userList = new LinkedList();
            conn = dsc.getPostgresDatasource().getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM users WHERE status = 0 ORDER BY id");
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("rfc"),
                        rs.getString("user"),
                        //rs.getString("password").toCharArray(),
                        rs.getString("mail"),
                        rs.getString("father_lastname"),
                        rs.getString("mother_lastname"),
                        rs.getString("given_name"),
                        rs.getString("city"),
                        rs.getString("entity"),
                        rs.getString("position"),
                        dsc.convertDate(rs.getTimestamp("register_date")),
                        dsc.convertDate(rs.getTimestamp("last_modification_date")),
                        rs.getInt("last_user_modified"),
                        dsc.convertDate(rs.getTimestamp("last_access_date")),
                        rs.getString("last_ip"),
                        rs.getString("curp"));
                userList.add(user);
            }
            /*
            Response response = Response.ok(userList.toArray(new User[userList.size()])).build();
            response.getHeaders().add("Access-Control-Allow-Origin", "*");
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization");
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            return response;
             */
            return Response.ok(userList.toArray(new User[userList.size()])).build();

            //return userList.toArray(new User[userList.size()]);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            //return Response.status(500).build();
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") long userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getPostgresDatasource().getConnection();
            st = conn.prepareStatement("SELECT * FROM users WHERE id = ? AND status = 0");
            st.setLong(1, userId);
            rs = st.executeQuery();
            User user;
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("rfc"),
                        rs.getString("user"),
                        //rs.getString("password").toCharArray(),
                        rs.getString("mail"),
                        rs.getString("father_lastname"),
                        rs.getString("mother_lastname"),
                        rs.getString("given_name"),
                        rs.getString("city"),
                        rs.getString("entity"),
                        rs.getString("position"),
                        dsc.convertDate(rs.getTimestamp("register_date")),
                        dsc.convertDate(rs.getTimestamp("last_modification_date")),
                        rs.getInt("last_user_modified"),
                        dsc.convertDate(rs.getTimestamp("last_access_date")),
                        rs.getString("last_ip"),
                        rs.getString("curp"));
            } else {
                return Response.status(Status.NOT_FOUND)
                        .entity(new HTTPJsonResponseObject(404, "Not Found",
                                "El usuario no existe")).build();
                //return Response.status(Status.NOT_FOUND).build();
            }
            //return user;
            return Response.ok(user).build();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            //return Response.status(500).build();
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(User user) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            java.util.Date now = new java.util.Date();
            conn = dsc.getPostgresDatasource().getConnection();
            st = conn.prepareStatement("INSERT INTO users("
                    + "rfc, \"user\", password, mail, father_lastname, mother_lastname, given_name,"
                    + "city, entity, position, register_date, last_modification_date, last_user_modified, "
                    + "last_access_date, last_ip, curp)"
                    + "VALUES (?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?) RETURNING id");
            st.setString(1, user.getRfc());
            st.setString(2, user.getUser());
            if (user.getPassword() != null) {
                st.setString(3, user.getPassword());
            } else {
                st.setNull(3, Types.VARCHAR);
            }
            st.setString(4, user.getMail());
            st.setString(5, user.getFather_lastname());
            st.setString(6, user.getMother_lastname());
            st.setString(7, user.getGiven_name());
            st.setString(8, user.getCity());
            st.setString(9, user.getEntity());
            st.setString(10, user.getPosition());
            if (user.getRegister_date() != null) {
                st.setDate(11, new java.sql.Date(user.getRegister_date().getTime()));
            } else {
                st.setDate(11, new java.sql.Date(now.getTime()));
            }

            if (user.getLast_modification_date() != null) {
                st.setDate(12, new java.sql.Date(user.getLast_modification_date().getTime()));
            } else {
                 st.setDate(12, new java.sql.Date(now.getTime()));
            }

            if (user.getLast_user_modified() != null) {
                st.setInt(13, user.getLast_user_modified());
            } else {
                st.setNull(13, Types.INTEGER);
            }

            if (user.getLast_access_date() != null) {
                st.setDate(14, new java.sql.Date(user.getLast_access_date().getTime()));
            } else {
                st.setNull(14, Types.DATE);
            }

            if (user.getLast_ip() != null) {
                st.setString(15, user.getLast_ip());
            } else {
                st.setNull(15, Types.OTHER);
            }
            
            if (user.getCurp() != null) {
                st.setString(16, user.getCurp());
            } else {
                st.setNull(16, Types.VARCHAR);
            }

            rs = st.executeQuery();
            if (rs.next()) {
                return Response.ok(rs.getString("id")).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new HTTPJsonResponseObject(500, "Internal Server Error",
                                "Error al insertar usuario")).build();
                //   return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Not inserted").build();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            //return Response.status(500).build();
            //throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
            throw new Registro3de3Exception(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

    @Path("{id}/toggleActiveStatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response toggleUserStatus(@PathParam("id") long userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getPostgresDatasource().getConnection();
            st = conn.prepareStatement("UPDATE users SET status = status * -1 - 1 WHERE id = ?");
            st.setLong(1, userId);

            int updateCount = st.executeUpdate();
            if (updateCount == 1) {
                return Response.ok(new HTTPJsonResponseObject(200, "OK", null)).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new HTTPJsonResponseObject(500, "Internal Server Error",
                                "Error al cambiar el estado del usuario")).build();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserLogically(@PathParam("id") long userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getPostgresDatasource().getConnection();
            st = conn.prepareStatement("UPDATE users SET status = -1 WHERE id = ?");
            st.setLong(1, userId);

            int updateCount = st.executeUpdate();
            if (updateCount == 1) {
                return Response.ok(new HTTPJsonResponseObject(200, "OK", null)).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new HTTPJsonResponseObject(500, "Internal Server Error",
                                "Error al borrar usuario")).build();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

}
