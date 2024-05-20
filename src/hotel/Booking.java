package hotel;

import java.util.Date;

/**
 * Booking class with 2 dates, from and to
 */
public class Booking {
    private Date fromDate;
    private Date toDate;

    public Booking(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}

