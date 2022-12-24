package rest;

import enums.AuthenticationMessage;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Credential;
import model.Employee;
import model.Token;
import org.primefaces.shaded.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Path("/auth")
@Dependent
@Stateless
public class AuthenticationManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName="timesheetdb-jpa")
    EntityManager em;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(Credential credential) {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.userName = :username", Employee.class);

        Employee employee = null;
        try {
            employee = query.setParameter("username", credential.getUserName()).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("username not found");
        }

        // username not found
        if (employee == null) {
            System.out.println("test");
            JSONObject json = new JSONObject();
            json.put("code", AuthenticationMessage.USERNAME_NOT_FOUND.ordinal());
            json.put("result", AuthenticationMessage.USERNAME_NOT_FOUND.getMessage());

            return Response.status(Response.Status.FORBIDDEN).entity(json.toString()).build();
        }

        // username found
        if (!employee.getPassword().equals(credential.getPassword())) {
            // wrong password
            JSONObject json = new JSONObject();
            json.put("code", AuthenticationMessage.WRONG_PASSWORD.ordinal());
            json.put("result", AuthenticationMessage.WRONG_PASSWORD.getMessage());

            return Response.status(Response.Status.FORBIDDEN).entity(json.toString()).build();
        } else {
            // login success
            String tokenString = issueToken(employee.getEmployeeNumber());
            LocalDateTime expiryDateTime = LocalDateTime.now().plusHours(1);

            Token token = new Token(tokenString, employee.getEmployeeNumber(), expiryDateTime);
            storeToken(token);

            TypedQuery<Token> tokenQuery = em.createQuery("select t from Token t where t.token = :tokenString", Token.class);
            Token queriedToken = tokenQuery.setParameter("tokenString", tokenString).getSingleResult();

            JSONObject json = new JSONObject();
            json.put("code", AuthenticationMessage.LOGIN_SUCCESS.ordinal());
            json.put("result", new JSONObject(queriedToken));

            return Response.status(Response.Status.OK).entity(json.toString()).build();
        }
    }

    public boolean validateToken(String tokenString) {
        TypedQuery<Token> query = em.createQuery("select t from Token t where t.token = :tokenString", Token.class);
        Token token = query.setParameter("tokenString", tokenString).getSingleResult();

        return (token.getExpiryDateTime().isAfter(LocalDateTime.now()));
    }

    private String issueToken(int employeeNumber) {
        final int TOKEN_LENGTH = 32;
        final String CHAR_LIST = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(employeeNumber);
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < TOKEN_LENGTH; ++i) {
            int randomNumber = secureRandom.nextInt(CHAR_LIST.length());
            char ch = CHAR_LIST.charAt(randomNumber);
            randomString.append(ch);
        }

        return randomString.toString();
    }

    private void storeToken(Token token) {
        deletePastToken(token);
        em.persist(token);
    }

    private void deletePastToken(Token token) {
        TypedQuery<Token> query = em.createQuery("select t from Token t where t.employeeNumber = :employeeNumber", Token.class);

        List<Token> tokenList = query.setParameter("employeeNumber", token.getEmployeeNumber()).getResultList();
        for (Token t : tokenList) {
            em.remove(t);
        }
    }
}