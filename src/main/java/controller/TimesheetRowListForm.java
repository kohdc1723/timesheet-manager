package controller;

import client.EmployeeClient;
import client.TimesheetRowClient;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.EditableTimesheetRow;
import model.Employee;
import model.Timesheet;
import model.TimesheetRow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("timesheetRowListForm")
@ConversationScoped
public class TimesheetRowListForm implements Serializable {
    /* member variables */
    @Inject
    private Conversation conversation;
    @Inject
    private TimesheetRowClient timesheetRowClient;
    @Inject
    private AuthenticationController authenticationController;
    private Employee referenceEmployee;
    private Timesheet referenceTimesheet;
    List<EditableTimesheetRow> list;

    /* constructors */
    public TimesheetRowListForm() {
    }

    /* methods */
    public String initTimesheetRowListForm(Employee employee, Timesheet timesheet) {
        conversation.begin();

        this.referenceEmployee = employee;
        this.referenceTimesheet = timesheet;

        return "timesheetRowListFormInit";
    }

    public float getSum() {
        float sum = 0;
        for (EditableTimesheetRow etsr : list) {
            sum += etsr.getSum();
        }

        return sum;
    }

    public float getSatSum() {
        float satSum = 0;
        for (EditableTimesheetRow etsr : list) {
            satSum += etsr.getTimesheetRow().getSatHours();
        }

        return satSum;
    }

    public float getSunSum() {
        float sunSum = 0;
        for (EditableTimesheetRow etsr : list) {
            sunSum += etsr.getTimesheetRow().getSunHours();
        }

        return sunSum;
    }

    public float getMonSum() {
        float monSum = 0;
        for (EditableTimesheetRow etsr : list) {
            monSum += etsr.getTimesheetRow().getMonHours();
        }

        return monSum;
    }

    public float getTueSum() {
        float tueSum = 0;
        for (EditableTimesheetRow etsr : list) {
            tueSum += etsr.getTimesheetRow().getTueHours();
        }

        return tueSum;
    }

    public float getWedSum() {
        float wedSum = 0;
        for (EditableTimesheetRow etsr : list) {
            wedSum += etsr.getTimesheetRow().getWedHours();
        }

        return wedSum;
    }

    public float getThuSum() {
        float thuSum = 0;
        for (EditableTimesheetRow etsr : list) {
            thuSum += etsr.getTimesheetRow().getThuHours();
        }

        return thuSum;
    }

    public float getFriSum() {
        float friSum = 0;
        for (EditableTimesheetRow etsr : list) {
            friSum += etsr.getTimesheetRow().getFriHours();
        }

        return friSum;
    }

    public void refreshList() {
        int timesheetId = referenceTimesheet.getTimesheetId();
        String tokenString = authenticationController.getToken().getToken();

        list = new ArrayList<>();
        List<TimesheetRow> timesheetRowList = timesheetRowClient.get(timesheetId, tokenString);
        for (TimesheetRow tsr : timesheetRowList) {
            EditableTimesheetRow etsr = new EditableTimesheetRow(tsr);
            list.add(etsr);
        }
    }

    public void addTimesheetRow() {
        int timesheetId = referenceTimesheet.getTimesheetId();
        EditableTimesheetRow editableTimesheetRow = new EditableTimesheetRow(timesheetId, true);
        list.add(editableTimesheetRow);
    }

    public void editTimesheetRow(EditableTimesheetRow editableTimesheetRow) {
        for (EditableTimesheetRow etsr : list) {
            if (etsr == editableTimesheetRow) {
                etsr.setEditable(true);
            }
        }
    }

    public void saveTimesheetRow(EditableTimesheetRow editableTimesheetRow) {
        String tokenString = authenticationController.getToken().getToken();
        // if new, insert timesheetRow
        if (editableTimesheetRow.getTimesheetRow().getTimesheetRowId() == 0) {
            timesheetRowClient.insert(editableTimesheetRow.getTimesheetRow(), tokenString);
        } else {
            // if not new, update changes of timesheetRow
            if (editableTimesheetRow.isEditable()) {
                timesheetRowClient.update(editableTimesheetRow.getTimesheetRow(), tokenString);
            }
        }

        editableTimesheetRow.setEditable(false);
        refreshList();
    }

    public void deleteTimesheetRow(EditableTimesheetRow editableTimesheetRow) {
        String tokenString = authenticationController.getToken().getToken();
        timesheetRowClient.delete(editableTimesheetRow.getTimesheetRow().getTimesheetRowId(), tokenString);
        list.remove(editableTimesheetRow);
    }

    public List<EditableTimesheetRow> getList() {
        if (list == null) {
            refreshList();
        }

        return list;
    }

    public void setList(List<EditableTimesheetRow> list) {
        this.list = list;
    }

    public Employee getReferenceEmployee() {
        return referenceEmployee;
    }

    public void setReferenceEmployee(Employee referenceEmployee) {
        this.referenceEmployee = referenceEmployee;
    }

    public Timesheet getReferenceTimesheet() {
        return referenceTimesheet;
    }

    public void setReferenceTimesheet(Timesheet referenceTimesheet) {
        this.referenceTimesheet = referenceTimesheet;
    }
}
