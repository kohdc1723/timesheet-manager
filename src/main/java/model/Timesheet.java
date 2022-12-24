package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Entity
@Table(name="Timesheets")
public class Timesheet implements Serializable {
    /* member variables */
    @Id
    @Column(name="TimesheetID")
    private int timesheetId;
    @Column(name="EmployeeNumber")
    private int employeeNumber;
    @Column(name="WeekEndDate")
    private LocalDate weekEndDate;

    /* constructors */
    public Timesheet() {
    }

    public Timesheet(int employeeNumber, LocalDate weekEndDate) {
        this.employeeNumber = employeeNumber;
        this.weekEndDate = weekEndDate;
    }

    /* getters and setters */
    public int getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public LocalDate getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(LocalDate weekEndDate) {
        this.weekEndDate = weekEndDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
    }
}
