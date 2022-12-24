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
import model.TimesheetRow;

import java.io.Serializable;
import java.util.List;

@Dependent
public class TimesheetRowClient implements Serializable {
    /* member variables */
    private final String baseUri;

    /* constructors */
    public TimesheetRowClient() {
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        baseUri = ex.getRequestScheme() + "://"
                + ex.getRequestServerName() + ":"
                + ex.getRequestServerPort()
                + ex.getRequestContextPath()
                + "/api/timesheetRow/";
    }

    /* methods */
    public List<TimesheetRow> get(int timesheetId, String tokenString) {
        String uri = baseUri + timesheetId + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        Response res = client.target(uri).request(MediaType.APPLICATION_JSON).get();
        List<TimesheetRow> timesheetRowList = res.readEntity(new GenericType<List<TimesheetRow>>() {});
        client.close();

        return timesheetRowList;
    }

    public void insert(TimesheetRow timesheetRow, String tokenString) {
        String uri = baseUri + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.json(timesheetRow));
        client.close();
    }

    public void update(TimesheetRow timesheetRow, String tokenString) {
        String uri = baseUri + timesheetRow.getTimesheetRowId() + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.json(timesheetRow));
        client.close();
    }

    public void delete(int timesheetRowId, String tokenString) {
        String uri = baseUri + timesheetRowId + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).delete();
        client.close();
    }
}
