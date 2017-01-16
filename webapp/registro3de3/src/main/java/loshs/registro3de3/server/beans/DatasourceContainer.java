package loshs.registro3de3.server.beans;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import org.postgresql.ds.PGPoolingDataSource;

@Singleton
public class DatasourceContainer {

    private static final PGPoolingDataSource DS = new PGPoolingDataSource();

    static final Logger LOGGER = Logger.getLogger(DatasourceContainer.class.getName());

    static {
        DS.setDataSourceName("A Data Source");
        DS.setServerName("localhost");
        DS.setPortNumber(5432);
        DS.setDatabaseName("registro3de3");
        DS.setUser("postgres");
        DS.setPassword("postgres");
        DS.setMaxConnections(10);
    }

    public DatasourceContainer() {

    }

    public PGPoolingDataSource getDatasource() {
        return DS;
    }

    public void close(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
