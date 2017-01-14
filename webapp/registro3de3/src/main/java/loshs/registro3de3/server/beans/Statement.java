package loshs.registro3de3.server.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Statement {
    private Integer id;
    private Integer user;
    private String user_name;
    private String user_position;
    private Short type;
    private Short status;
    private Date date;
    private String entity;
    private String folio_number;
   
    public Statement(){

    }

    public Statement(Integer id, Integer user, String user_name, String user_position, 
            Short type, Short status, Date date, String entity, /*From user*/
            String folio_number) {
        this.id = id;
        this.user = user;
        this.user_name = user_name;
        this.user_position = user_position;
        this.type = type;
        this.status = status;
        this.date = date;
        this.entity = entity; // From user
        this.folio_number = folio_number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_position() {
        return user_position;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getFolio_number() {
        return folio_number;
    }

    public void setFolio_number(String folio_number) {
        this.folio_number = folio_number;
    }

    

}
