package commands.hotel_commands;

import commands.CommandClass;
import exceptions.InvalidDateRangeException;
import singletons.Hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class HotelCommand extends CommandClass {
    private Hotel hotel;

    public HotelCommand(Scanner scanner) {
        super(scanner);
        this.hotel = Hotel.getInstance();
    }

    public Hotel getHotel() {
        return hotel;
    }

    protected Date[] parseAndValidateDates(String fromDateString, String toDateString) throws ParseException, InvalidDateRangeException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date[] dates = new Date[2];
        dates[0] = dateFormat.parse(fromDateString);
        dates[1] = dateFormat.parse(toDateString);

        if(dates[0].after(dates[1])) {
            throw new InvalidDateRangeException("Invalid date range! The 'from' date cannot be after the 'to' date.");
        }
        return dates;
    }

    protected Date[] parseAndValidateDatesFromParts(String fromDateString, String toDateString) throws ParseException, InvalidDateRangeException {
        return parseAndValidateDates(fromDateString, toDateString);
    }
}
