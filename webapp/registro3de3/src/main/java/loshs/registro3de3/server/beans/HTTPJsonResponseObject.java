package loshs.registro3de3.server.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HTTPJsonResponseObject {
    public Integer status_code;
    public String status_message;
    public String message;
    
    public HTTPJsonResponseObject(){

    }

    public HTTPJsonResponseObject(Integer status_code, String status_message, String message) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.message = message;
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
    
}
