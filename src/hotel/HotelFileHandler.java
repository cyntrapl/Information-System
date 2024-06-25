package hotel;

import enums.Activities;
import singletons.CurrentFile;
import singletons.Hotel;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Class that handles reading and writing room data to and from a file
 */
public class HotelFileHandler {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ROOMS_FILE = "rooms.txt";

    private Hotel hotel;
    private CurrentFile currentFile;
    private List<HotelRoom> rooms;

    /**
     * Constructor for HotelFileHandler
     */
    public HotelFileHandler() {
        this.hotel = Hotel.getInstance();
        this.currentFile = CurrentFile.getInstance();
        this.rooms = hotel.getRooms();
    }

    /**
     * Saves the rooms to the current file
     */
    public void saveReservationsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFile.getCurrentFileName()))) {
            for (HotelRoom room : rooms) {
                writer.println("RoomNumber: " + room.getRoomNumber());
                writer.println("Beds: " + room.getBeds());
                for (Reservation reservation : room.getReservations()) {
                    writer.println("Reservation:");
                    writer.println("    Note: " + reservation.getNote());
                    writer.println("    Guests: " + reservation.getGuests());
                    writer.println("    Dates: " + dateFormat.format(reservation.getFromDate()) + "," + dateFormat.format(reservation.getToDate()));
                    if(reservation.getActivities() != null){
                        writer.println("    Activities: " + reservation.getActivities().stream()
                                .map(activity -> activity.name().replace("_", " ").toLowerCase())
                                .collect(Collectors.joining(", ")));
                    }else writer.println("    Activities: ");
                    writer.println("    Available: " + reservation.isAvailable());
                }
                // Separate rooms with a blank line
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the rooms from the current file
     */
    public void loadReservationsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFile.getCurrentFileName()))) {
            String line;
            HotelRoom room = null;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    // Empty line indicates the end of room data, reset the current room
                    room = null;
                } else {
                    String[] parts = line.split(": ");
                    switch (parts[0]) {
                        case "RoomNumber":
                            int roomNumber = Integer.parseInt(parts[1]);
                            room = hotel.findRoomByNumber(roomNumber);
                            reader.readLine(); // Skip the bed line
                            break;
                        case "Reservation:":
                            if (room != null) {
                                Reservation reservation = new Reservation(new ArrayList<>(), "", 0, new Date(), new Date());
                                reservation.setNote(reader.readLine().split(": ")[1]);
                                reservation.setGuests(Integer.parseInt(reader.readLine().split(": ")[1]));
                                String[] dateParts = reader.readLine().split(": ")[1].split(",");
                                reservation.setFromDate(dateFormat.parse(dateParts[0]));
                                reservation.setToDate(dateFormat.parse(dateParts[1]));
                                parts =  reader.readLine().split(": ");
                                if(parts.length != 1){
                                    reservation.setActivities(parseActivities(parts[1]));
                                }
                                reservation.setAvailable(Boolean.parseBoolean(reader.readLine().split(": ")[1]));
                                room.addReservation(reservation);
                            }
                            break;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses a string of activities into a list of activities
     * @param activitiesString the string of activities
     * @return the list of activities
     */
    private List<Activities> parseActivities(String activitiesString) {
        List<Activities> activities = new ArrayList<>();
        String[] activityArray = activitiesString.split(", ");
        for (String activity : activityArray) {
            try {
                activities.add(Activities.valueOf(activity.trim().toUpperCase().replace(" ", "_")));
            } catch (IllegalArgumentException e) {
                //System.out.println("Invalid activity: " + activity);
            }
        }
        return activities;
    }

    /**
     * Loads the rooms from the base file
     */
    public void loadRoomsBaseFile() {
        try (Scanner scanner = new Scanner(new File(ROOMS_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" ");
                int roomNumber = Integer.parseInt(parts[0]);
                int beds = Integer.parseInt(parts[1]);
                hotel.addRoom(new HotelRoom(roomNumber, beds));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

