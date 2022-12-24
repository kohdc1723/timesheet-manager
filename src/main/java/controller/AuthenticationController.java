package controller;

import client.AuthenticationClient;
import enums.AuthenticationMessage;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.Credential;
import model.Token;
import org.primefaces.shaded.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;

@Named("authenticationController")
@SessionScoped
public class AuthenticationController implements Serializable {
    /* member variables */
    @Inject
    private AuthenticationClient authenticationClient;
    private Token token;

    /* constructor */
    public AuthenticationController() {
    }

    /* methods */
    public boolean authenticateUser(Credential credential) {
        String response = authenticationClient.authenticateUser(credential);

        JSONObject jsonResponse = new JSONObject(response);
        int code = jsonResponse.getInt("code");

        if (code == 0) {
            JSONObject jsonToken = jsonResponse.getJSONObject("result");

            int tokenId = jsonToken.getInt("tokenId");
            LocalDateTime expiryDateTime = LocalDateTime.parse(jsonToken.getString("expiryDateTime"));
            String tokenString = jsonToken.getString("token");
            int employeeNumber = jsonToken.getInt("employeeNumber");

            Token token = new Token(tokenId, tokenString, employeeNumber, expiryDateTime);
            setToken(token);

            return true;
        } else {
            String message = jsonResponse.getString("result");

            if (code == 1) {
                FacesContext.getCurrentInstance().addMessage("loginForm:username",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            } else {
                FacesContext.getCurrentInstance().addMessage("loginForm:password",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }

            return false;
        }
    }

    public void logout() {
        setToken(null);
    }

    public Token getToken() {
        return this.token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
