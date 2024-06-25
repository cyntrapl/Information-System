package singletons;

import hotel.HotelRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class for the hotel
 */
public class Hotel {
    private static volatile Hotel instance;
    private List<HotelRoom> rooms;

    /**
     * Constructor for the Hotel class
     */
    private Hotel() {
        rooms = new ArrayList<>();
    }

    /**
     * Gets the instance of the Hotel class
     * @return the instance of the Hotel class
     */
    public static Hotel getInstance() {

        Hotel result = instance;
        if (result != null) {
            return result;
        }
        synchronized(CurrentFile.class) {
            if (instance == null) {
                instance = new Hotel();
            }
            return instance;
        }
    }

    /**
     * Clears all rooms
     */
    public void clearAllRooms() {
        rooms.clear();
    }

    /**
     * Adds a room to the hotel
     * @param room the room to add
     */
    public void addRoom(HotelRoom room) {
        rooms.add(room);
    }

    /**
     * Gets the rooms in the hotel
     * @return the rooms in the hotel
     */
    public List<HotelRoom> getRooms() {
        return rooms;
    }

    /**
     * Finds a room by its number
     * @param roomNumber the room number
     * @return the room with the given number
     */
    public HotelRoom findRoomByNumber(int roomNumber) {
        for (HotelRoom room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    /**
     * Removes a room by its number
     * @param roomNumber the room number
     */
    public void removeRoomByRoomNumber(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }
}