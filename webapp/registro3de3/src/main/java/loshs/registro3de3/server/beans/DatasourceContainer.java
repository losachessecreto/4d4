/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loshs.registro3de3.server.beans;

import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author hugo
 */
public class DatasourceContainer {

    public static final PGPoolingDataSource DS = new PGPoolingDataSource();

    static {
        DS.setDataSourceName("A Data Source");
        DS.setServerName("localhost");
        DS.setPortNumber(5432);
        DS.setDatabaseName("test");
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
