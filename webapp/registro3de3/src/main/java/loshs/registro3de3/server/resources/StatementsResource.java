package loshs.registro3de3.server.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import loshs.registro3de3.server.beans.Statement;
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

@ManagedBean
@Path("statements")
public class StatementsResource {

    @Context
    DatasourceContainer dsc;

    static final Logger LOGGER = Logger.getLogger(StatementsResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatements() {
        Connection conn = null;
        java.sql.Statement st = null;
        ResultSet rs = null;
        try {
            LinkedList<Statement> statementList = new LinkedList();
            conn = dsc.getDatasource().getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT s.id, s.user, u.user as user_name, "
                    + "u.position as user_position, s.type, s.status, s.date, "
                    + "u.entity, s.folio_number "
                    + "FROM statements s JOIN users u ON s.user = u.id "
                    + "WHERE s.status >= 0 AND u.status >= 0 "
                    + "ORDER BY s.id");
            while (rs.next()) {
                Statement statement = new Statement(
                        rs.getInt("id"),
                        rs.getInt("user"),
                        rs.getString("user_name"),
                        rs.getString("user_position"),
                        rs.getShort("type"),
                        rs.getShort("status"),
                        rs.getTimestamp("date") != null ? new Date(rs.getTimestamp("date").getTime()) : null,
                        rs.getString("entity"),
                        rs.getString("folio_number"));
                statementList.add(statement);
            }
            return Response.ok(statementList.toArray(new Statement[statementList.size()])).build();
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
    public Response getStatement(@PathParam("id") long statementId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getDatasource().getConnection();
            st = conn.prepareStatement("SELECT s.id, s.user, u.user as user_name, "
                    + "u.position as user_position, s.type, s.status, s.date, "
                    + "u.entity, s.folio_number "
                    + "FROM statements s JOIN users u ON s.user = u.id "
                    + "WHERE s.status >= 0 AND u.status >= 0 AND s.id = ?");
            st.setLong(1, statementId);
            rs = st.executeQuery();
            Statement statement;
            if (rs.next()) {
                statement = new Statement(
                        rs.getInt("id"),
                        rs.getInt("user"),
                        rs.getString("user_name"),
                        rs.getString("user_position"),
                        rs.getShort("type"),
                        rs.getShort("status"),
                        rs.getTimestamp("date") != null ? new Date(rs.getTimestamp("date").getTime()) : null,
                        rs.getString("entity"),
                        rs.getString("folio_number"));
            } else {
                return Response.status(Status.NOT_FOUND)
                        .entity(new HTTPJsonResponseObject(404, "Not Found",
                                "La declaración no existe")).build();
            }
            return Response.ok(statement).build();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postStatement(Statement statement) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getDatasource().getConnection();
            st = conn.prepareStatement("INSERT INTO statements("
                    + "status, folio_number, \"user\", type, date) "
                    + "VALUES (?, ?, ?, ?, ?) RETURNING id");
            st.setShort(1, statement.getStatus());
            st.setString(2, statement.getFolio_number());
            st.setInt(3, statement.getUser());
            st.setShort(4, statement.getType());
            if (statement.getDate() != null) {
                st.setDate(5, new java.sql.Date(statement.getDate().getTime()));
            } else {
                st.setNull(5, Types.DATE);
            }
            rs = st.executeQuery();
            if (rs.next()) {
                return Response.ok(rs.getString("id")).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new HTTPJsonResponseObject(500, "Internal Server Error",
                                "Error al insertar declaración")).build();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

    @Path("{id}/toggleActiveStatus")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response toggleStatementStatus(@PathParam("id") long statementId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getDatasource().getConnection();
            st = conn.prepareStatement("UPDATE statements SET status = status * -1 WHERE id = ?");
            st.setLong(1, statementId);

            int updateCount = st.executeUpdate();
            if (updateCount == 1) {
                return Response.ok(new HTTPJsonResponseObject(200, "OK", null)).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new HTTPJsonResponseObject(500, "Internal Server Error",
                                "Error al cambiar el estado de la declaración")).build();
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
    public Response deleteStatementLogically(@PathParam("id") long statementId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = dsc.getDatasource().getConnection();
            st = conn.prepareStatement("UPDATE statements SET status = -1 WHERE id = ?");
            st.setLong(1, statementId);

            int updateCount = st.executeUpdate();
            if (updateCount == 1) {
                return Response.ok(new HTTPJsonResponseObject(200, "OK", null)).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new HTTPJsonResponseObject(500, "Internal Server Error",
                                "Error al borrar declaración")).build();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
    }

    @Path("/fromUser/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserStatements(@PathParam("id") long userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            LinkedList<Statement> statementList = new LinkedList();
            conn = dsc.getDatasource().getConnection();
            st = conn.prepareStatement("SELECT s.id, s.user, u.user as user_name, "
                    + "u.position as user_position, s.type, s.status, s.date, "
                    + "u.entity, s.folio_number "
                    + "FROM statements s JOIN users u ON s.user = u.id "
                    + "WHERE s.status >= 0 AND u.status >= 0 AND u.id = ?");
            st.setLong(1, userId);
            rs = st.executeQuery();
            while (rs.next()) {
                Statement statement = new Statement(
                        rs.getInt("id"),
                        rs.getInt("user"),
                        rs.getString("user_name"),
                        rs.getString("user_position"),
                        rs.getShort("type"),
                        rs.getShort("status"),
                        rs.getTimestamp("date") != null ? new Date(rs.getTimestamp("date").getTime()) : null,
                        rs.getString("entity"),
                        rs.getString("folio_number"));
                statementList.add(statement);
            }
            return Response.ok(statementList.toArray(new Statement[statementList.size()])).build();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).build());
        } finally {
            dsc.close(conn, st, rs);
        }
        //return Response.status(Status.NOT_IMPLEMENTED).entity(new HTTPJsonResponseObject(501, "Not Implemented",
        //                        "Método no implementado aún")).build();
    }

}
