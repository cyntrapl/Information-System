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
import java.util.stream.Collectors;

/**
 * writing and reading hotel data from the file
 */
public class HotelRoomFileHandler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Hotel hotel;
    private CurrentFile currentFile;
    private List<HotelRoom> rooms;

    public HotelRoomFileHandler() {
        this.hotel = Hotel.getInstance();
        this.currentFile = CurrentFile.getInstance();
        rooms = hotel.getRooms();
    }

    public void saveRoomsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFile.getCurrentFileName()))) {
            for (HotelRoom room : rooms) {
                writer.println("RoomNumber: " + room.getRoomNumber());
                writer.println("Beds: " + room.getBeds());
                writer.println("Note: " + room.getNote());
                writer.println("Guests: " + room.getGuest());
                writer.println("Booking: " + dateFormat.format(room.getBooking().getFromDate()) + "," + dateFormat.format(room.getBooking().getToDate()));
                writer.println("Activities: " + room.getActivities().stream()
                        .map(activity -> activity.name().replace("_", " ").toLowerCase())
                        .collect(Collectors.joining(", ")));
                writer.println("Available: " + room.isAvailable());
                // Separate rooms with a blank line1
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HotelRoom> loadRoomsFromFile() {
        List<HotelRoom> rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFile.getCurrentFileName()))) {
            String line;
            HotelRoom room = null;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    // Empty line indicates the end of room data, add the current room to the list
                    if (room != null) {
                        rooms.add(room);
                    }
                    room = null;
                } else {
                    String[] parts = line.split(": ");
                    switch (parts[0]) {
                        case "RoomNumber":
                            /*
                            * RoomNumber: 1
                            Beds: 1
                            Note: Simpsons
                            Guests: 1
                            Booking: 0017-08-15,0020-08-15
                            Activities: ..
                            Available: true
                            * */
                            int roomNumber = Integer.parseInt(parts[1]);
                            int beds = Integer.parseInt(reader.readLine().split(": ")[1]);
                            String note = reader.readLine().split(": ")[1];
                            int guests = Integer.parseInt(reader.readLine().split(": ")[1]);
                            String[] bookingParts =  reader.readLine().split(": ")[1].split(",");
                            Date fromDate = dateFormat.parse(bookingParts[0]);
                            Date toDate = dateFormat.parse(bookingParts[1]);
                            String activitiesString = reader.readLine().substring("Activities:".length());
                            List<Activities> activities = parseActivities(activitiesString);
                            room = new HotelRoom(roomNumber, beds, note, new Booking(fromDate, toDate), guests, activities);
                            break;
                        case "Available":
                            boolean available = Boolean.parseBoolean(parts[1]);
                            room.setAvailable(available);
                            break;
                    }
                }
            }
            // Add the last room to the list if not added already
            if (room != null) {
                rooms.add(room);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return rooms;
    }

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
}

