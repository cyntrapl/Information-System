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
 * Класът HotelFileHandler се използва за записване и зареждане на данни за хотелски стаи във и от файл.
 */
public class HotelFileHandler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Hotel hotel;
    private CurrentFile currentFile;
    private List<HotelRoom> rooms;

    /**
     * Конструира нов обект HotelFileHandler.
     */
    public HotelFileHandler() {
        this.hotel = Hotel.getInstance();
        this.currentFile = CurrentFile.getInstance();
        this.rooms = hotel.getRooms();
    }

    /**
     * Записва данните за хотелската стая в отворения в момента файл.
     */
    public void saveRoomsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFile.getCurrentFileName()))) {
            for (HotelRoom room : rooms) {
                writer.println("RoomNumber: " + room.getRoomNumber());
                writer.println("Beds: " + room.getBeds());
                writer.println("Note: " + room.getReservation().getNote());
                writer.println("Guests: " + room.getReservation().getGuests());
                writer.println("Date: " + dateFormat.format(room.getReservation().getFromDate()) + "," + dateFormat.format(room.getReservation().getToDate()));
                if(room.getReservation().getActivities() != null){
                    writer.println("Activities: " + room.getReservation().getActivities().stream()
                            .map(activity -> activity.name().replace("_", " ").toLowerCase())
                            .collect(Collectors.joining(", ")));
                }else writer.println("Activities: ");
                // Separate rooms with a blank line1
                writer.println("Available: " + room.isAvailable());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Зарежда данните за хотелската стая от отворения в момента файл.
     * @return a list of HotelRoom objects
     */
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
                            String[] dateParts =  reader.readLine().split(": ")[1].split(",");
                            Date fromDate = dateFormat.parse(dateParts[0]);
                            Date toDate = dateFormat.parse(dateParts[1]);
                            String activitiesString = reader.readLine().substring("Activities:".length());
                            List<Activities> activities = parseActivities(activitiesString);
                            room = new HotelRoom(roomNumber, beds, new Reservation(activities, note, guests, fromDate, toDate));
                            boolean available = Boolean.parseBoolean(reader.readLine().split(": ")[1]);
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

    /**
     * Разработва низ от дейности и връща списък с обекти Activities.
     * @param activitiesString низът от дейности
     * @връща списък с обекти Activities
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
}

