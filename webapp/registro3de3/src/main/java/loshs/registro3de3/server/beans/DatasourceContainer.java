package loshs.registro3de3.server.beans;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.postgresql.ds.PGPoolingDataSource;

@Singleton
public class DatasourceContainer {

    private static final PGPoolingDataSource POSTGRES_DATASOURCE = new PGPoolingDataSource();

    private static final String ALFRESCO_URL = "http://192.168.15.5:8080/alfresco/api/-default-/public/cmis/versions/1.1/browser";
    private static final String ALFRESCO_USER = "admin";
    private static final String ALFRESCO_PASS = "celulares";
    private static final String ALFRESCO_REPO = "-default-";
    private static final SessionFactory ALFRESCO_SESSION_FACTORY;
    private static final  Map<String, String> ALFRESCO_PARAMETERS;

    static final Logger LOGGER = Logger.getLogger(DatasourceContainer.class.getName());

    static {
        POSTGRES_DATASOURCE.setDataSourceName("A Data Source");
        POSTGRES_DATASOURCE.setServerName("localhost");
        POSTGRES_DATASOURCE.setPortNumber(5432);
        POSTGRES_DATASOURCE.setDatabaseName("registro3de3");
        POSTGRES_DATASOURCE.setUser("postgres");
        POSTGRES_DATASOURCE.setPassword("postgres");
        POSTGRES_DATASOURCE.setMaxConnections(10);

        ALFRESCO_PARAMETERS = new HashMap<>();
        ALFRESCO_PARAMETERS.put(SessionParameter.USER, ALFRESCO_USER);
        ALFRESCO_PARAMETERS.put(SessionParameter.PASSWORD, ALFRESCO_PASS);
        ALFRESCO_PARAMETERS.put(SessionParameter.BROWSER_URL, ALFRESCO_URL);
        ALFRESCO_PARAMETERS.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
        ALFRESCO_PARAMETERS.put(SessionParameter.REPOSITORY_ID, ALFRESCO_REPO);
        ALFRESCO_SESSION_FACTORY = SessionFactoryImpl.newInstance();
    }

    public DatasourceContainer() {

    }

    public PGPoolingDataSource getPostgresDatasource() {
        return POSTGRES_DATASOURCE;
    }
    
    public SessionFactory getAlfrescoSessionFactory() {
        return ALFRESCO_SESSION_FACTORY;
    }
    
    public Map getAlfrescoParemeters() {
        return ALFRESCO_PARAMETERS;
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

    public java.util.Date convertDate(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return new java.util.Date(date.getTime());
    }

    public java.sql.Date convertDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

}
