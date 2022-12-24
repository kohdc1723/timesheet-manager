package model;

import java.io.Serializable;

public class EditableTimesheetRow implements Serializable {
    /* member variables */
    private TimesheetRow timesheetRow;
    private boolean editable;

    /* constructors */
    public EditableTimesheetRow() {
        this.editable = false;
    }

    public EditableTimesheetRow(int timesheetId, boolean editable) {
        this.timesheetRow = new TimesheetRow();
        this.timesheetRow.setTimesheetId(timesheetId);
        this.editable = editable;
    }

    public EditableTimesheetRow(TimesheetRow timesheetRow) {
        this.timesheetRow = timesheetRow;
        this.editable = false;
    }

    /* methods */
    public float getSum() {
        return timesheetRow.getSum();
    }

    public TimesheetRow getTimesheetRow() {
        return timesheetRow;
    }

    public void setTimesheetRow(TimesheetRow timesheetRow) {
        this.timesheetRow = timesheetRow;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
