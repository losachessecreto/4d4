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
  
    public static final PGPoolingDataSource ds = new PGPoolingDataSource();

    static {
        ds.setDataSourceName("A Data Source");
        ds.setServerName("localhost");
        ds.setPortNumber(5432);
        ds.setDatabaseName("test");
        ds.setUser("postgres");
        ds.setPassword("postgres");
        ds.setMaxConnections(10);
    }
        
    public DatasourceContainer(){
    }

    public PGPoolingDataSource getDatasource() {
        return ds;
    }
}
