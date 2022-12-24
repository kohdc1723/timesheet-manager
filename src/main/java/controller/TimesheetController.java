package controller;

import client.TimesheetClient;
import client.TimesheetRowClient;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.Timesheet;
import model.TimesheetRow;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Named("timesheetController")
@SessionScoped
public class TimesheetController implements Serializable {
    /* member variables */
    private static final float HOURS_PER_WEEK = 40;
    @Inject
    private TimesheetClient timesheetClient;
    @Inject
    private AuthenticationController authenticationController;
    @Inject
    private TimesheetRowClient timesheetRowClient;
    private LocalDate weekEndDateInput;
    private Timesheet newTimesheet;
    private Timesheet editableTimesheet;

    /* constructors */
    public TimesheetController() {
        newTimesheet = new Timesheet();
    }

    /* methods */
    public float getTotalHours(Timesheet timesheet) {
        int timesheetId = timesheet.getTimesheetId();
        String tokenString = authenticationController.getToken().getToken();

        List<TimesheetRow> timesheetRowList = timesheetRowClient.get(timesheetId, tokenString);

        float totalHours = 0;
        for (TimesheetRow tsr : timesheetRowList) {
            totalHours += tsr.getSum();
        }

        return totalHours;
    }

    public float getOverTime(Timesheet timesheet) {
        return getTotalHours(timesheet) > HOURS_PER_WEEK ? (getTotalHours(timesheet) - HOURS_PER_WEEK) : 0;
    }

    public List<Timesheet> getTimesheets(int employeeNumber) {
        String tokenString = authenticationController.getToken().getToken();
        return timesheetClient.get(employeeNumber, tokenString);
    }

    public void insertTimesheet(Timesheet timesheet) {
        String tokenString = authenticationController.getToken().getToken();
        int employeeNumber = authenticationController.getToken().getEmployeeNumber();

        timesheet.setEmployeeNumber(employeeNumber);

        timesheetClient.insert(timesheet, tokenString);
    }

    public void validateTimesheet(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        int currentEmployeeNumber = (Integer) component.getAttributes().get("currentEmployeeNumber");

        List<Timesheet> timesheetList = getTimesheets(currentEmployeeNumber);
        for (Timesheet ts : timesheetList) {
            if (ts.getWeekEndDate().isEqual(LocalDate.parse(value.toString()).with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)))) {
                throw new ValidatorException(new FacesMessage("WeekEndDate must be unique"));
            }
        }
    }

    public void deleteTimesheet(Timesheet timesheet) {
        String tokenString = authenticationController.getToken().getToken();
        timesheetClient.delete(timesheet.getTimesheetId(), tokenString);
    }

    public LocalDate getWeekEndDateInput() {
        return weekEndDateInput;
    }

    public void setWeekEndDateInput(LocalDate weekEndDateInput) {
        this.weekEndDateInput = weekEndDateInput;
    }

    public Timesheet getEditableTimesheet() {
        return editableTimesheet;
    }

    public String setEditableTimesheet(Timesheet editableTimesheet) {
        this.editableTimesheet = editableTimesheet;

        return "editTimesheet";
    }

    public Timesheet getNewTimesheet() {
        return newTimesheet;
    }

    public void setNewTimesheet(Timesheet newTimesheet) {
        this.newTimesheet = newTimesheet;
    }
}
