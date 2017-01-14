package loshs.registro3de3.server.beans;

import org.postgresql.ds.PGPoolingDataSource;

public class DatasourceContainer {

    public static final PGPoolingDataSource DS = new PGPoolingDataSource();

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
}
