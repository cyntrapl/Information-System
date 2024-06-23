package hotel;

import enums.Activities;

import java.util.Date;
import java.util.List;

public class Reservation {
    private List<Activities> activities;
    private String note;
    private int guests;
    private Date fromDate;
    private Date toDate;

    public Reservation(List<Activities> activities, String note, int guests, Date fromDate, Date toDate) {
        this.activities = activities;
        this.note = note;
        this.guests = guests;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public List<Activities> getActivities() {
        return activities;
    }

    public String getNote() {
        return note;
    }

    public int getGuests() {
        return guests;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}
