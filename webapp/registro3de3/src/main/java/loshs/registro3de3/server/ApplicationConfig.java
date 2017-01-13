/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loshs.registro3de3.server;

import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import loshs.registro3de3.server.beans.DatasourceContainer;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.Injections;

/**
 *
 * @author hugo
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Inject
    public ApplicationConfig(ServiceLocator serviceLocator) {
        System.out.println("Registering injectables...");
        
        DynamicConfiguration dc = Injections.getConfiguration(serviceLocator);

        // singleton binding
        Injections.addBinding(
                Injections.newBinder(DatasourceContainer.class)
                .to(DatasourceContainer.class)
                .in(Singleton.class),
                dc);

        // singleton instance binding
        Injections.addBinding(
                //Injections.newBinder(new Pool())
                Injections.newBinder(new DatasourceContainer())
                .to(DatasourceContainer.class),
                dc);

        dc.commit();
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(loshs.registro3de3.server.CORSFilter.class);
        resources.add(loshs.registro3de3.server.resources.StatementsResource.class);
        resources.add(loshs.registro3de3.server.resources.UsersResource.class);
    }

}
