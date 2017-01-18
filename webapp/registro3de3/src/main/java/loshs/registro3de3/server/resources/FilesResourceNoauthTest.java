package loshs.registro3de3.server.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.Normalizer;
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
import loshs.registro3de3.server.beans.DatasourceContainer;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@ManagedBean
@Path("test/file")
public class FilesResourceNoauthTest {

    @Context
    DatasourceContainer dsc;

    static final Logger LOGGER = Logger.getLogger(FilesResourceNoauthTest.class.getName());

    @GET
    @Path("download")
    public Response download(@QueryParam("path") final String filepath) {
        final String[] fp = new String[1];
        if (filepath == null || filepath.length() == 0) {
            fp[0] = "/tmp/sc2.jpg";
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
        String UPLOAD_PATH = "/tmp/";
        String mediaType = "";
        try {
            int read;
            byte[] bytes = new byte[1024];
            String uploadedName = Normalizer.normalize(fileMetaData.getFileName(), Normalizer.Form.NFD);
            uploadedName = uploadedName.replaceAll("[^\\p{ASCII}]", "");
            File tempFile = new File(UPLOAD_PATH + uploadedName);
            try (OutputStream out = new FileOutputStream(tempFile)) {
                while ((read = fileInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
            }
            mediaType = new loshs.registro3de3.server.FileTypeDetector().probeContentType(tempFile.toPath());
        } catch (IOException e) {
            throw new WebApplicationException("Error al cargar el archivo. Por favor intente nuevamente");
        }
        return Response.ok("The file is " +  mediaType).build();
    }

}
