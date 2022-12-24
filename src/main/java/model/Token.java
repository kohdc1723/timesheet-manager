package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement(name="token")
@Entity
@Table(name="Tokens")
public class Token implements Serializable {
    /* member variables */
    @Id
    @Column(name="TokenID")
    private int tokenId;
    @Column(name="Token")
    private String token;
    @Column(name="EmployeeNumber")
    private int employeeNumber;
    @Column(name="ExpiryDateTime")
    private LocalDateTime expiryDateTime;

    /* constructors */
    public Token() {
    }

    public Token(String token, int employeeNumber, LocalDateTime expiryDateTime) {
        this.token = token;
        this.employeeNumber = employeeNumber;
        this.expiryDateTime = expiryDateTime;
    }

    public Token(int tokenId, String token, int employeeNumber, LocalDateTime expiryDateTime) {
        this.tokenId = tokenId;
        this.token = token;
        this.employeeNumber = employeeNumber;
        this.expiryDateTime = expiryDateTime;
    }

    /* getters and setters */
    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }
}
