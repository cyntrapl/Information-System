package hotel;

/**
 * hotel room with all the needed data
 */
public class HotelRoom {
    private int roomNumber;
    private int beds;
    private boolean available;
    private Reservation reservation;

    public HotelRoom(int roomNumber, int beds, Reservation reservation) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.reservation = reservation;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}


