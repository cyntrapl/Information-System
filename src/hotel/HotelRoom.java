package hotel;

import java.util.HashSet;
import java.util.Set;

/**
 * class HotelRoom
 * Represents a hotel room
 */
public class HotelRoom {
    private int roomNumber;
    private int beds;
    private Set<Reservation> reservations;

    /**
     * Constructor for HotelRoom
     * @param roomNumber int object
     * @param beds int object
     */
    public HotelRoom(int roomNumber, int beds) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.reservations = new HashSet<>();
    }

    /**
     * Method that returns the room number
     * @return int object
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Method that returns the number of beds
     * @return int object
     */
    public int getBeds() {
        return beds;
    }

    /**
     * Method that returns the reservations
     * @return Set object
     */
    public Set<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Method that adds a reservation
     * @param reservation Reservation object
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    /**
     * Method that removes a reservation
     * @param reservation Reservation object
     */
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }
}