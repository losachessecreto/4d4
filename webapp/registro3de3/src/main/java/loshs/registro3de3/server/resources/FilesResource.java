package loshs.registro3de3.server.resources;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import loshs.registro3de3.server.beans.AlfrescoDocumentObject;
import loshs.registro3de3.server.beans.DatasourceContainer;
import loshs.registro3de3.server.beans.HTTPJsonResponseObject;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@ManagedBean
@Path("file")
public class FilesResource {

    @Context
    DatasourceContainer dsc;

    static final Logger LOGGER = Logger.getLogger(FilesResource.class.getName());

    private static final String ALFRESCO_3_DE_3_FOLDER = "Registro 3 de 3";

    @POST
    @Path("download")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response download(@FormParam("objectId") String objectId, @FormParam("fileName") String fileName) {
        final AlfrescoDocumentObject docu = new AlfrescoDocumentObject(objectId, fileName);
        return download(docu);
    }
    
    
    @POST
    @Path("download")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response download(final AlfrescoDocumentObject docu) {
        final Object[][] ret = new Object[1][];
        try {
            StreamingOutput fileStream = new StreamingOutput() {
                @Override
                public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
                    ret[0] = getFromAlfresco(docu.getObjectId(), docu.getFileName());
                    InputStream input = new BufferedInputStream((InputStream)ret[0][0]);
                    int read;
                    while ((read = input.read()) != -1) {
                        output.write(read);
                    }
                    output.flush();
                }
            };
            LOGGER.log(Level.INFO, "ret = {0}", ret);
             LOGGER.log(Level.INFO, "ret[0] = {0}", ret[0]);
             LOGGER.log(Level.INFO, "file stream ret[0][o]= {0}", (String)ret[0][0]);
             LOGGER.log(Level.INFO, "file id ret[0][1]= {0}", (String)ret[0][1]);
            LOGGER.log(Level.INFO, "file name ret[0][2]= {0}", (String)ret[0][2]);
            return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                    .header("content-disposition", "attachment; filename = " + (String)ret[0][2]).build();
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(e);
        }
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPdfFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {
        String UPLOAD_PATH = "/tmp/registro3de3/";
        String mediaType;
        Object[] ret = new Object[]{null, 500, "Internal Server Error"};
        File tempFile;
        try {
            int read;
            byte[] bytes = new byte[1024];
            String uploadedName = Normalizer.normalize(fileMetaData.getFileName(), Normalizer.Form.NFD);
            uploadedName = uploadedName.replaceAll("[^\\p{ASCII}]", "");
            tempFile = new File(UPLOAD_PATH + uploadedName);
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                while ((read = fileInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
            }
            mediaType = new loshs.registro3de3.server.FileTypeDetector().probeContentType(tempFile.toPath());
            ret = putToAlfresco(tempFile, mediaType);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
        return Response.ok(new HTTPJsonResponseObject((Integer) ret[1], (String) ret[2],
                "El documento se cargó correctamente ",
                new AlfrescoDocumentObject((String) ret[0], tempFile.getName(), ALFRESCO_3_DE_3_FOLDER, mediaType))).build();
    }

    private Object[] putToAlfresco(File postedFile, String mediaType) throws FileNotFoundException {
        Object[] retVal = new Object[3];

        Session session = dsc.getAlfrescoSessionFactory().createSession(dsc.getAlfrescoParemeters());
        Folder root = session.getRootFolder();
        Folder alfresco3de3folder = (Folder) session.getObjectByPath(root.getPath(), ALFRESCO_3_DE_3_FOLDER);
        if (session.existsPath(alfresco3de3folder.getPath(), postedFile.getName())) {
            CmisObject o = session.getObjectByPath(alfresco3de3folder.getPath(), postedFile.getName());
            o.delete();
            retVal[1] = 200;
            retVal[2] = "Ok";
        } else {
            retVal[1] = 201;
            retVal[2] = "Created";
        }
        Map<String, Object> properties = new HashMap<>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, postedFile.getName());
        InputStream stream = new BufferedInputStream(new FileInputStream(postedFile));
        ContentStream contentStream = new ContentStreamImpl(postedFile.getName(), BigInteger.valueOf(postedFile.length()), mediaType, stream);
        Document newDoc = alfresco3de3folder.createDocument(properties, contentStream, VersioningState.MAJOR);
        retVal[0] = newDoc.getId();
        return retVal;
    }

    private Object[] getFromAlfresco(String objectId, String name) {
        Object[] retVal = new Object[3];
        Session session = dsc.getAlfrescoSessionFactory().createSession(dsc.getAlfrescoParemeters());
        Document document;
        if (objectId != null) {
            if (session.exists(objectId)) {
                document = (Document) session.getObject(session.createObjectId(objectId));
            } else {
                return null;
            }
        } else {
            if (name == null) {
                throw new IllegalArgumentException("Alguno de los parametros objectId o fileName debe no ser nulo.");
            }
            Folder root = session.getRootFolder();
            Folder alfresco3de3folder = (Folder) session.getObjectByPath(root.getPath(), ALFRESCO_3_DE_3_FOLDER);
            if (session.existsPath(alfresco3de3folder.getPath(), name)) {
                document = (Document) session.getObjectByPath(alfresco3de3folder.getPath(), name);
            } else {
                return null;
            }
        }
        InputStream stream = document.getContentStream().getStream();
        retVal[0] = stream;
        retVal[1] = document.getId();
        retVal[2] = document.getName();
        return retVal;
    }

}
