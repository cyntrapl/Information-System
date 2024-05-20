package hotel;

import enums.Activities;

import java.util.List;

/**
 * hotel room with all the needed data
 */
public class HotelRoom {
    private int roomNumber;
    private int beds;
    private List<Activities> activities;
    private String notes;
    private int guests;
    private Booking bookings;
    private boolean available;

    public HotelRoom(int roomNumber, int beds, String notes, Booking bookings, int guests, List<Activities> activities) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.notes = notes;
        this.bookings = bookings;
        this.guests = guests;
        this.available = true;
        this.activities = activities;
    }

    public int  getGuest() {
        return guests;
    }

    public Booking getBooking() {
        return bookings;
    }

    public int getBeds() {
        return beds;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getNote() {
        return notes;
    }

    public List<Activities> getActivities() {
        return activities;
    }

    public void setGuest(int guests) {
        this.guests = guests;
    }

    public void setBooking(Booking bookings) {
        this.bookings = bookings;
    }

    public void setNote(String notes) {
        this.notes = notes;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}


