package hotel;

/**
 * Класът HotelRoom е клас за обектите, които представляват стая в хотел.
 */
public class HotelRoom {
    private int roomNumber;
    private int beds;
    private boolean available;
    private Reservation reservation;

    /**
     * Създава нова хотелска стая с номер, брой легла и резервация.
     * @param roomNumber номер на стаята
     * @param beds брой легла
     * @param reservation резервация
     */
    public HotelRoom(int roomNumber, int beds, Reservation reservation) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.reservation = reservation;
        this.available = true;
    }

    /**
     * Връща номера на стаята.
     * @return номер на стаята
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Връща броя на леглата.
     * @return броя на леглата
     */
    public int getBeds() {
        return beds;
    }

    /**
     * Проверява дали стаята е свободна.
     * @return дали стаята е свободна
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Задава дали стаята е свободна.
     * @param available дали стаята е свободна
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Връща резервацията на стаята.
     * @return резервация на стаята
     */
    public Reservation getReservation() {
        return reservation;
    }
}


