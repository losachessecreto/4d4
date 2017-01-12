package loshs.registro3de3.server.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
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
import loshs.registro3de3.server.beans.User;

/**
 * Root resource (exposed at "myresource" path)
 */
@ManagedBean
@Path("users")
public class UsersResource {

    @Context
    DatasourceContainer dsc;

    static final Logger LOGGER = Logger.getLogger(UsersResource.class.getName());

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            LinkedList<User> userList = new LinkedList();
            conn = dsc.getDatasource().getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM users WHERE status = 0 ORDER BY id");
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("user"),
                        rs.getString("password").toCharArray(),
                        rs.getString("mail"),
                        rs.getString("father_lastname"),
                        rs.getString("mother_lastname"),
                        rs.getString("given_name"),
                        rs.getTimestamp("register_date") != null ? new Date(rs.getTimestamp("register_date").getTime()) : null,
                        rs.getTimestamp("last_modification_date") != null ? new Date(rs.getTimestamp("last_modification_date").getTime()) : null,
                        rs.getInt("last_user_modified"),
                        rs.getTimestamp("last_access_date") != null ? new Date(rs.getTimestamp("last_access_date").getTime()) : null,
                        rs.getString("last_ip"));
                userList.add(user);
            }
            return Response.ok(userList.toArray(new User[userList.size()])).build();
            //return userList.toArray(new User[userList.size()]);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            //return Response.status(500).build();
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
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
            conn = dsc.getDatasource().getConnection();
            st = conn.prepareStatement("SELECT * FROM users WHERE id = ? AND status = 0");
            st.setLong(1, userId);
            rs = st.executeQuery();
            User user;
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("user"),
                        rs.getString("password").toCharArray(),
                        rs.getString("mail"),
                        rs.getString("father_lastname"),
                        rs.getString("mother_lastname"),
                        rs.getString("given_name"),
                        rs.getTimestamp("register_date") != null ? new Date(rs.getTimestamp("register_date").getTime()) : null,
                        rs.getTimestamp("last_modification_date") != null ? new Date(rs.getTimestamp("last_modification_date").getTime()) : null,
                        rs.getInt("last_user_modified"),
                        rs.getTimestamp("last_access_date") != null ? new Date(rs.getTimestamp("last_access_date").getTime()) : null,
                        rs.getString("last_ip"));
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
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(User user) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getDatasource().getConnection();
            st = conn.prepareStatement("INSERT INTO users("
                    + "\"user\", password, mail, father_lastname, mother_lastname, "
                    + "given_name, register_date, last_modification_date, last_user_modified, "
                    + "last_access_date, last_ip)"
                    + "VALUES (?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, "
                    + "?, ?) RETURNING id");
            st.setString(1, user.getUser());
            st.setString(2, new String(user.getPassword()));
            st.setString(3, user.getMail());
            st.setString(4, user.getFather_lastname());
            st.setString(5, user.getMother_lastname());
            st.setString(6, user.getGiven_name());
            if (user.getRegister_date() != null) {
                st.setDate(7, new java.sql.Date(user.getRegister_date().getTime()));
            } else {
                st.setNull(7, Types.DATE);
            }

            if (user.getLast_modification_date() != null) {
                st.setDate(8, new java.sql.Date(user.getLast_modification_date().getTime()));
            } else {
                st.setNull(8, Types.DATE);
            }

            if (user.getLast_user_modified() != null) {
                st.setInt(9, user.getLast_user_modified());
            } else {
                st.setNull(9, Types.INTEGER);
            }

            if (user.getLast_access_date() != null) {
                st.setDate(10, new java.sql.Date(user.getLast_access_date().getTime()));
            } else {
                st.setNull(10, Types.DATE);
            }

            if (user.getLast_ip() != null) {
                st.setString(11, user.getLast_ip());
            } else {
                st.setNull(11, Types.OTHER);
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
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
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
            conn = dsc.getDatasource().getConnection();
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
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
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
            conn = dsc.getDatasource().getConnection();
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
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    @Path("test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User[] testResource() {
        Date d = new Date();

        return new User[]{
            new User(0, "user", new char[]{}, "mail", "father_lastname", "mother_lastname",
            "given_name", d, d, 0, d, "last_ip"),
            new User(0, "user2", new char[]{}, "mail", "father_lastname", "mother_lastname",
            "given_name", d, d, 0, d, "last_ip")};
    }

}