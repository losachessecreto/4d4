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
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
@Path("test/file")
public class FilesResourceNoauthTest {

    @Context
    DatasourceContainer dsc;

    static final Logger LOGGER = Logger.getLogger(FilesResource.class.getName());

    private static final String ALFRESCO_3_DE_3_FOLDER = "Registro 3 de 3";

    @GET
    @Path("download")
    public Response download(@QueryParam("path") final String filepath) {
        final String[] fp = new String[1];
        if (filepath == null || filepath.length() == 0) {
            fp[0] = "/tmp/myfile";
        } else {
            fp[0] = filepath;
        }

        StreamingOutput fileStream = new StreamingOutput() {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
                java.nio.file.Path path = Paths.get(fp[0]);
                FileInputStream input = new FileInputStream(path.toFile());
                int read;
                while ((read = input.read()) != -1) {
                    output.write(read);
                }
                output.flush();
            }
        };
        String fileName = fp[0].lastIndexOf("/") >= 0 ? fp[0].substring(fp[0].lastIndexOf("/") + 1) : fp[0];
        return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = " + fileName).build();
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPdfFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {
        String UPLOAD_PATH = "/tmp/registro3de3";
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
            throw new WebApplicationException("Error al cargar el archivo. Por favor intente nuevamente");
        }
        return Response.ok(new HTTPJsonResponseObject((Integer) ret[1], (String) ret[2],
                "El documento se creo correctamente ",
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
        Document newDoc = root.createDocument(properties, contentStream, VersioningState.MAJOR);

        retVal[0] = newDoc.getId();
        return retVal;
    }

}
