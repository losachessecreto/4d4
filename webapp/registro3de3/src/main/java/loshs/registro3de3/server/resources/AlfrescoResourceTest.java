package loshs.registro3de3.server.resources;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import loshs.registro3de3.server.beans.DatasourceContainer;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

@ManagedBean
@Path("test/alfresco")
public class AlfrescoResourceTest {

    @Context
    DatasourceContainer dsc;

    static final Logger LOGGER = Logger.getLogger(AlfrescoResourceTest.class.getName());

    @GET
    public Response create() {
        Session session = dsc.getAlfrescoSessionFactory().createSession(dsc.getAlfrescoParemeters());

        Folder root = session.getRootFolder();

        Map<String, Object> properties = new HashMap<>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        properties.put(PropertyIds.NAME, "Registro 3 de 3");

        //Folder newFolder = root.createFolder(properties);

        String name = "kimiresume.pdf";

        // properties 
        // (minimal set: name and object type id)
        Map<String, Object> propertiesff = new HashMap<>();
        propertiesff.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        propertiesff.put(PropertyIds.NAME, name);

        // content
        byte[] content = "Kiwi!".getBytes();
        InputStream stream = new ByteArrayInputStream(content);
        ContentStream contentStream = new ContentStreamImpl(name, BigInteger.valueOf(content.length), "text/plain", stream);

        // create a major version
        Document newDoc = root.createDocument(propertiesff, contentStream, VersioningState.MAJOR);

        return Response.ok("The id of the new document is " + newDoc.getId()).build();
    }

    @GET
    @Path("listroot")
    @Produces(MediaType.TEXT_HTML)
    public Response list(@QueryParam("path") final String filepath) {
        Session session = dsc.getAlfrescoSessionFactory().createSession(dsc.getAlfrescoParemeters());

        Folder root = session.getRootFolder();

        ItemIterable<CmisObject> children = root.getChildren();

        String s = "<html><body><ul>";

        for (CmisObject o : children) {
            s += "<li>" + o.getName() + "</li>";
            //System.out.println(o.getName());
        }

        s += "</ul></body></html>";

        return Response.ok(s).build();
    }

    @GET
    @Path("delete")
    public Response delete() {
        Session session = dsc.getAlfrescoSessionFactory().createSession(dsc.getAlfrescoParemeters());

        Folder root = session.getRootFolder();

        String name = "kimiresume.pdf";

        if (session.existsPath(root.getPath(), name)) {
            CmisObject o = session.getObjectByPath(root.getPath(), name);
            session.delete(o);
        } else {
            return Response.ok("Did not exist").build();
        }

        return Response.ok("Deleted " + root.getPath() + " " + name).build();
    }

}
