package controller;

import client.EmployeeClient;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.Employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("employeeController")
@SessionScoped
public class EmployeeController implements Serializable {
    /* member variables */
    @Inject
    private EmployeeClient employeeClient;
    @Inject
    private AuthenticationController authenticationController;
    private Employee currentEmployee;
    private Employee editableEmployee;
    private Employee newEmployee;

    /* constructors */
    public EmployeeController() {
        newEmployee = new Employee();
    }

    /* methods */
    public Employee getEmployee(int employeeNumber) {
        String tokenString = authenticationController.getToken().getToken();

        return employeeClient.get(employeeNumber, tokenString);
    }

    public List<Employee> getAllEmployees() {
        String tokenString = authenticationController.getToken().getToken();

        return employeeClient.getAll(tokenString);
    }

    public List<Employee> getRegularEmployees() {
        List<Employee> employeeList = getAllEmployees();
        employeeList.removeIf(Employee::isAdmin);

        return employeeList;
    }

    public String insertEmployee(Employee employee) {
        String tokenString = authenticationController.getToken().getToken();
        employeeClient.insert(employee, tokenString);

        return "user-list";
    }

    public void updateEmployee(Employee employee) {
        String tokenString = authenticationController.getToken().getToken();
        employeeClient.update(employee, tokenString);

        FacesContext.getCurrentInstance().addMessage("growl",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Edit Success"));
    }

    public void deleteEmployee(int employeeNumber) {
        String tokenString = authenticationController.getToken().getToken();
        employeeClient.delete(employeeNumber, tokenString);
    }

    public boolean isAdmin(int employeeNumber) {
        return getEmployee(employeeNumber).isAdmin();
    }

    public void validateUserName(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String inputUserName = value.toString().trim();

        List<Employee> employeeList = getAllEmployees();
        List<String> userNameList = new ArrayList<>();
        for (Employee e : employeeList) {
            userNameList.add(e.getUserName());
        }

        if (component.getAttributes().get("exceptionEmployeeNumber") != null) {
            int exceptionEmployeeNumber = (Integer) component.getAttributes().get("exceptionEmployeeNumber");
            String exceptionUserName = getEmployee(exceptionEmployeeNumber).getUserName();
            userNameList.remove(exceptionUserName);
        }

        if (userNameList.contains(inputUserName)) {
            throw new ValidatorException(new FacesMessage("username already exists"));
        }
    }

    public Employee getCurrentEmployee() {
        if (this.currentEmployee == null) {
            initCurrentEmployee();
        }

        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public void initCurrentEmployee() {
        this.currentEmployee = getEmployee(authenticationController.getToken().getEmployeeNumber());
    }

    public Employee getEditableEmployee() {
        return editableEmployee;
    }

    public String setEditableEmployee(Employee editableEmployee) {
        this.editableEmployee = editableEmployee;

        return "editEmployee";
    }

    public Employee getNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }
}
