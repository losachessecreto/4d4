package loshs.registro3de3.server.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HTTPJsonResponseObject {
    private Integer status_code;
    private String status_message;
    private String message;
    private Boolean success;
    private AlfrescoDocumentObject document;
    
    public HTTPJsonResponseObject(){

    }

    public HTTPJsonResponseObject(Integer status_code, String status_message, String message) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.message = message;
    }
    
    public HTTPJsonResponseObject(Integer status_code, String status_message, String message, Boolean success) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.message = message;
        this.success = success;
    }
    
    public HTTPJsonResponseObject(Integer status_code, String status_message, String message, AlfrescoDocumentObject document) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.message = message;
        this.document = document;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }
    
    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public AlfrescoDocumentObject getDocument() {
        return document;
    }

    public void setDocument(AlfrescoDocumentObject document) {
        this.document = document;
    }

    
}
