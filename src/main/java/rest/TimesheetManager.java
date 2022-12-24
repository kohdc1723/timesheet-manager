package rest;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Employee;
import model.Timesheet;
import org.primefaces.shaded.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Path("/timesheet")
@Dependent
@Stateless
public class TimesheetManager implements Serializable {
    @PersistenceContext(unitName="timesheetdb-jpa")
    EntityManager em;
    @Inject
    private AuthenticationManager authenticationManager;

    @GET
    @Path("{empNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("empNum") int employeeNumber, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t where t.employeeNumber = :employeeNumber", Timesheet.class);
        List<Timesheet> timesheetList = query.setParameter("employeeNumber", employeeNumber).getResultList();

        return Response.status(Response.Status.OK).entity(timesheetList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("token") String token, Timesheet timesheet) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        em.persist(timesheet);
        String message = "timesheet insert success";
        return Response.status(Response.Status.OK).entity(message).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int timesheetId, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        String message = "timesheet delete success";
        Timesheet timesheet = em.find(Timesheet.class, timesheetId);
        em.remove(timesheet);

        return Response.status(Response.Status.OK).entity(message).build();
    }
}
