package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "TimesheetRows")
public class TimesheetRow implements Serializable {
    /* member variables */
    @Id
    @Column(name = "TimesheetRowID")
    private int timesheetRowId;
    @Column(name = "TimesheetID")
    private int timesheetId;
    @Column(name = "ProjectTitle")
    private String projectTitle;
    @Column(name = "SatHours")
    private float satHours;
    @Column(name = "SunHours")
    private float sunHours;
    @Column(name = "MonHours")
    private float monHours;
    @Column(name = "TueHours")
    private float tueHours;
    @Column(name = "WedHours")
    private float wedHours;
    @Column(name = "ThuHours")
    private float thuHours;
    @Column(name = "FriHours")
    private float friHours;
    @Column(name = "Notes")
    private String notes;

    /* constructors */
    public TimesheetRow() {
    }

    /* methods */
    public float getSum() {
        return (satHours + sunHours + monHours + tueHours + wedHours + thuHours + friHours);
    }

    public int getTimesheetRowId() {
        return timesheetRowId;
    }

    public void setTimesheetRowId(int timesheetRowId) {
        this.timesheetRowId = timesheetRowId;
    }

    public int getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public float getSatHours() {
        return satHours;
    }

    public void setSatHours(float satHours) {
        this.satHours = satHours;
    }

    public float getSunHours() {
        return sunHours;
    }

    public void setSunHours(float sunHours) {
        this.sunHours = sunHours;
    }

    public float getMonHours() {
        return monHours;
    }

    public void setMonHours(float monHours) {
        this.monHours = monHours;
    }

    public float getTueHours() {
        return tueHours;
    }

    public void setTueHours(float tueHours) {
        this.tueHours = tueHours;
    }

    public float getWedHours() {
        return wedHours;
    }

    public void setWedHours(float wedHours) {
        this.wedHours = wedHours;
    }

    public float getThuHours() {
        return thuHours;
    }

    public void setThuHours(float thuHours) {
        this.thuHours = thuHours;
    }

    public float getFriHours() {
        return friHours;
    }

    public void setFriHours(float friHours) {
        this.friHours = friHours;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
