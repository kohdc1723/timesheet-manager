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
import model.Timesheet;
import model.TimesheetRow;
import org.primefaces.shaded.json.JSONObject;

import java.io.Serializable;
import java.util.List;

@Path("/timesheetRow")
@Dependent
@Stateless
public class TimesheetRowManager implements Serializable {
    @PersistenceContext(unitName = "timesheetdb-jpa")
    EntityManager em;
    @Inject
    private AuthenticationManager authenticationManager;

    @GET
    @Path("{timesheetId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("timesheetId") int timesheetId, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from TimesheetRow tsr where tsr.timesheetId = :timesheetId", TimesheetRow.class);
        List<TimesheetRow> timesheetRowList = query.setParameter("timesheetId", timesheetId).getResultList();

        return Response.status(Response.Status.OK).entity(timesheetRowList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(TimesheetRow timesheetRow, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        em.persist(timesheetRow);
        String message = "timesheetRow insert success";
        return Response.status(Response.Status.OK).entity(message).build();
    }

    @PUT
    @Path("{timesheetRowId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("timesheetRowId") int timesheetRowId, TimesheetRow timesheetRow, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        em.merge(timesheetRow);
        String message = "timesheetRow update success";
        return Response.status(Response.Status.OK).entity(message).build();
    }

    @DELETE
    @Path("{timesheetRowId}")
    public Response delete(@PathParam("timesheetRowId") int timesheetRowId, @QueryParam("token") String token) {
        if (!authenticationManager.validateToken(token)) {
            JSONObject json = new JSONObject();
            json.put("result", "invalid token");

            return Response.status(Response.Status.UNAUTHORIZED).entity(json.toString()).build();
        }

        String message = "timesheetRow delete success";
        TimesheetRow timesheetRow = em.find(TimesheetRow.class, timesheetRowId);
        em.remove(timesheetRow);

        return Response.status(Response.Status.OK).entity(message).build();
    }
}
