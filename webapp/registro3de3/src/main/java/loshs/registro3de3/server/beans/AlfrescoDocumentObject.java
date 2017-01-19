package loshs.registro3de3.server.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlfrescoDocumentObject {
    private String objectId;
    private String fileName;
    private String path;
    private String mediaType;
    
    public AlfrescoDocumentObject(){

    }
    
    public AlfrescoDocumentObject(String objectId, String fileName) {
        this.objectId = objectId;
        this.fileName = fileName;
    }

    public AlfrescoDocumentObject(String objectId, String fileName, String path, String mediaType) {
        this.objectId = objectId;
        this.fileName = fileName;
        this.path = path;
        this.mediaType = mediaType;
    }

   
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    
    
}
