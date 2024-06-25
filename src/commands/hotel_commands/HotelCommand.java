package commands.hotel_commands;

import commands.CommandClass;
import exceptions.InvalidDateRangeException;
import singletons.Hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * class HotelCommand extends CommandClass
 * Abstract class that extends CommandClass
 * Contains the hotel singleton
 */
public abstract class HotelCommand extends CommandClass {
    private Hotel hotel;

    /**
     * Constructor for HotelCommand
     * @param scanner Scanner object used to read user input
     */
    public HotelCommand(Scanner scanner) {
        super(scanner);
        this.hotel = Hotel.getInstance();
    }

    /**
     * Getter for hotel
     * @return Hotel object
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Method that parses and validates dates
     * @param fromDateString String representing the 'from' date
     * @param toDateString String representing the 'to' date
     * @return Date[] array containing the parsed dates
     * @throws ParseException if the date cannot be parsed
     * @throws InvalidDateRangeException if the date range is invalid
     */
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

    /**
     * Method that parses and validates dates from parts
     * @param fromDateString String representing the 'from' date
     * @param toDateString String representing the 'to' date
     * @return Date[] array containing the parsed dates
     * @throws ParseException if the date cannot be parsed
     * @throws InvalidDateRangeException if the date range is invalid
     */
    protected Date[] parseAndValidateDatesFromParts(String fromDateString, String toDateString) throws ParseException, InvalidDateRangeException {
        return parseAndValidateDates(fromDateString, toDateString);
    }
}
