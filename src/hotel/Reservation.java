package hotel;

import enums.Activities;

import java.util.Date;
import java.util.List;

/**
 * Класът Reservation съдържа информация за резервацията на стая в хотел.
 */
public class Reservation {
    private List<Activities> activities;
    private String note;
    private int guests;
    private Date fromDate;
    private Date toDate;

    /**
     * Създава нова резервация с дейности, бележка, брой гости и дати на настаняване и напускане.
     * @param activities дейности
     * @param note бележка
     * @param guests брой гости
     * @param fromDate дата на настаняване
     * @param toDate дата на напускане
     */
    public Reservation(List<Activities> activities, String note, int guests, Date fromDate, Date toDate) {
        this.activities = activities;
        this.note = note;
        this.guests = guests;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Връща списък с дейности.
     * @return списък с дейности
     */
    public List<Activities> getActivities() {
        return activities;
    }

    /**
     * Връща бележката.
     * @return бележката
     */
    public String getNote() {
        return note;
    }

    /**
     * Връща броя на гостите.
     * @return броя на гостите
     */
    public int getGuests() {
        return guests;
    }

    /**
     * Връща датата на настаняване.
     * @return датата на настаняване
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Връща датата на напускане.
     * @return датата на напускане
     */
    public Date getToDate() {
        return toDate;
    }
}
