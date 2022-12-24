package model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.primefaces.shaded.json.JSONPropertyIgnore;

import java.io.Serializable;

@XmlRootElement(name="employee")
@Entity
@Table(name="Employees")
public class Employee implements Serializable {
    /* member variables */
    @Id
    @Column(name="EmployeeNumber")
    private int employeeNumber;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="UserName")
    private String userName;
    @Column(name="Password")
    private String password;
    @Column(name="IsAdmin")
    private boolean admin;

    /* constructors */
    public Employee() {
    }

    /* getters and setters */
    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
