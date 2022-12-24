package client;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Credential;

import java.io.Serializable;

@Dependent
public class AuthenticationClient implements Serializable {
    /* member variables */
    private final String baseUri;

    /* constructors */
    public AuthenticationClient() {
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        baseUri = ex.getRequestScheme() + "://"
                + ex.getRequestServerName() + ":"
                + ex.getRequestServerPort()
                + ex.getRequestContextPath()
                + "/api/auth/";
    }

    /* methods */
    public String authenticateUser(Credential credential) {
        Client client = ClientBuilder.newClient();
        Response res = client.target(baseUri).request(MediaType.APPLICATION_JSON).post(Entity.json(credential));
        String result = res.readEntity(String.class);
        client.close();

        return result;
    }
}
