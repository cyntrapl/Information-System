package hotel;

import enums.Activities;

import java.util.Date;
import java.util.List;

/**
 * Class that represents a reservation
 */
public class Reservation {
    private List<Activities> activities;
    private String note;
    private int guests;
    private Date fromDate;
    private Date toDate;
    private boolean available;

    /**
     * Constructor for Reservation
     * @param activities List object
     * @param note String object
     * @param guests int object
     * @param fromDate Date object
     * @param toDate Date object
     */
    public Reservation(List<Activities> activities, String note, int guests, Date fromDate, Date toDate) {
        this.activities = activities;
        this.note = note;
        this.guests = guests;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.available = true;
    }

    /**
     * Method that returns the activities
     * @return List object
     */
    public List<Activities> getActivities() {
        return activities;
    }

    /**
     * Method that returns the note
     * @return String object
     */
    public String getNote() {
        return note;
    }

    /**
     * Method that returns the number of guests
     * @return int object
     */
    public int getGuests() {
        return guests;
    }

    /**
     * Method that returns the start date
     * @return Date object
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Method that returns the end date
     * @return Date object
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * Method that returns if the reservation is available
     * @return boolean object
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Method that sets the availability of the reservation
     * @param available boolean object
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Method that sets the activities
     * @param activities List object
     */
    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

    /**
     * Method that sets the note
     * @param note String object
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Method that sets the number of guests
     * @param guests int object
     */
    public void setGuests(int guests) {
        this.guests = guests;
    }

    /**
     * Method that sets the start date
     * @param fromDate Date object
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Method that sets the end date
     * @param toDate Date object
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
