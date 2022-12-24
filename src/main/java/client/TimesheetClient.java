package client;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Timesheet;

import java.io.Serializable;
import java.util.List;

@Dependent
public class TimesheetClient implements Serializable {
    /* member variables */
    private final String baseUri;

    /* constructors */
    public TimesheetClient() {
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        baseUri = ex.getRequestScheme() + "://"
                + ex.getRequestServerName() + ":"
                + ex.getRequestServerPort()
                + ex.getRequestContextPath()
                + "/api/timesheet/";
    }

    /* methods */
    public List<Timesheet> get(int employeeNumber, String token) {
        String uri = baseUri + employeeNumber + "?token=" + token;

        Client client = ClientBuilder.newClient();
        Response res = client.target(uri).request(MediaType.APPLICATION_JSON).get();
        List<Timesheet> timesheetList = res.readEntity(new GenericType<List<Timesheet>>() {});
        client.close();

        return timesheetList;
    }

    public void insert(Timesheet timesheet, String token) {
        String uri = baseUri + "?token=" + token;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.json(timesheet));
        client.close();
    }

    public void delete(int timesheetId, String token) {
        String uri = baseUri + timesheetId + "?token=" + token;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).delete();
        client.close();
    }
}
