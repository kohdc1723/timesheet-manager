package model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name="credential")
@Named("credential")
@RequestScoped
public class Credential implements Serializable {
    /* member variables */
    private String userName;
    private String password;

    /* constructors */
    public Credential() {
    }

    public Credential(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /* getters and setters */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
