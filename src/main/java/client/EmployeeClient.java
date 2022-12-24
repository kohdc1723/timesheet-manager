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
import model.Employee;

import java.io.Serializable;
import java.util.List;

@Dependent
public class EmployeeClient implements Serializable {
    /* member variables */
    private final String baseUri;

    /* constructors */
    public EmployeeClient() {
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        baseUri = ex.getRequestScheme() + "://"
                + ex.getRequestServerName() + ":"
                + ex.getRequestServerPort()
                + ex.getRequestContextPath()
                + "/api/employee/";
    }

    /* methods */
    public Employee get(int employeeNumber, String tokenString) {
        String uri = baseUri + employeeNumber + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        Response res = client.target(uri).request(MediaType.APPLICATION_JSON).get();
        Employee employee = res.readEntity(Employee.class);
        client.close();

        return employee;
    }

    public List<Employee> getAll(String tokenString) {
        String uri = baseUri + "all?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        Response res = client.target(uri).request(MediaType.APPLICATION_JSON).get();
        List<Employee> employeeList = res.readEntity(new GenericType<>() {});
        client.close();

        return employeeList;
    }

    public void insert(Employee employee, String tokenString) {
        String uri = baseUri + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).post(Entity.json(employee));
        client.close();
    }

    public void update(Employee employee, String tokenString) {
        String uri = baseUri + employee.getEmployeeNumber() + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).put(Entity.json(employee));
        client.close();
    }

    public void delete(int employeeNumber, String tokenString) {
        String uri = baseUri + employeeNumber + "?token=" + tokenString;

        Client client = ClientBuilder.newClient();
        client.target(uri).request(MediaType.APPLICATION_JSON).delete();
        client.close();
    }
}
