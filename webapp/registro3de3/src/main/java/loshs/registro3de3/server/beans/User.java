/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loshs.registro3de3.server.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hugo
 */
@XmlRootElement
public class User {
    public Integer id;
    public String user;
    public char[] password;
    public String mail;
    public String father_lastname;
    public String mother_lastname;
    public String given_name;
    public String position;
    public Date register_date;
    public Date last_modification_date;
    public Integer last_user_modified;
    public Date last_access_date;
    public String last_ip;
   
    public User(){

    }

    public User(Integer id, String user, char[] password, String mail, String father_lastname, 
            String mother_lastname, String given_name, String position, Date register_date, 
            Date last_modification_date, Integer last_user_modified, Date last_access_date, String last_ip) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.mail = mail;
        this.father_lastname = father_lastname;
        this.mother_lastname = mother_lastname;
        this.given_name = given_name;
        this.position = position;
        this.register_date = register_date;
        this.last_modification_date = last_modification_date;
        this.last_user_modified = last_user_modified;
        this.last_access_date = last_access_date;
        this.last_ip = last_ip;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFather_lastname() {
        return father_lastname;
    }

    public void setFather_lastname(String father_lastname) {
        this.father_lastname = father_lastname;
    }

    public String getMother_lastname() {
        return mother_lastname;
    }

    public void setMother_lastname(String mother_lastname) {
        this.mother_lastname = mother_lastname;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }
    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public Date getLast_modification_date() {
        return last_modification_date;
    }

    public void setLast_modification_date(Date last_modification_date) {
        this.last_modification_date = last_modification_date;
    }

    public Integer getLast_user_modified() {
        return last_user_modified;
    }

    public void setLast_user_modified(Integer last_user_modified) {
        this.last_user_modified = last_user_modified;
    }

    public Date getLast_access_date() {
        return last_access_date;
    }

    public void setLast_access_date(Date last_access_date) {
        this.last_access_date = last_access_date;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }
    
}
