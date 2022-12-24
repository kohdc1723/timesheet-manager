package rest;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Employee;
import org.primefaces.shaded.json.JSONObject;

import java.io.Serializable;
import java.util.List;

@Path("/employee")
@Stateless
@Dependent
public class EmployeeManager implements Serializable {
    @PersistenceContext(unitName = "timesheetdb-jpa")
    private EntityManager em;
    @Inject
    private AuthenticationManager authenticationManager;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class);
        List<Employee> employeeList = query.getResultList();

        return Response.status(Response.Status.OK).entity(employeeList).build();
    }

    @GET
    @Path("{empNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("empNum") int employeeNumber, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        Employee employee = em.find(Employee.class, employeeNumber);

        return Response.status(Response.Status.OK).entity(employee).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Employee employee, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        String message = "employee insert success";
        em.persist(employee);

        return Response.status(Response.Status.OK).entity(message).build();
    }

    @PUT
    @Path("{empNum}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("empNum") int employeeNumber, Employee employee, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        String message = "employee update success";
        em.merge(employee);

        return Response.status(Response.Status.OK).entity(message).build();
    }

    @DELETE
    @Path("{empNum}")
    public Response delete(@PathParam("empNum") int employeeNumber, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        String message = "employee delete success";
        Employee employee = em.find(Employee.class, employeeNumber);
        em.remove(employee);

        return Response.status(Response.Status.OK).entity(message).build();
    }
}
