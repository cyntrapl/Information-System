package singletons;

import hotel.Booking;
import hotel.HotelRoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * handles the hotel and it's rooms
 */
public class Hotel {
    private static volatile Hotel instance;
    private List<HotelRoom> rooms;
    private int numberOfRooms;

    private Hotel() {
        rooms = new ArrayList<>();
        numberOfRooms = 0;
    }

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

    public int getNumberOfRooms() {
        if(numberOfRooms == 0) {
            Scanner fileScanner = null;
            File file = new File("rooms.txt");
            try {
                fileScanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            while(fileScanner.hasNextLine()) {
                numberOfRooms++;
                fileScanner.nextLine();
            }
            return numberOfRooms;
        }else return numberOfRooms;
    }

    public void loadAllRooms(List<HotelRoom> roomList) {
        rooms.addAll(roomList);
    }

    public void clearAllRooms() {
        rooms.clear();
    }

    public void addRoom(HotelRoom room) {
        rooms.add(room);
    }

    public List<HotelRoom> getRooms() {
        return rooms;
    }

    public HotelRoom findRoomByNumber(int roomNumber) {
        for (HotelRoom room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void removeRoomByRoomNumber(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }

    public void removeRoom(HotelRoom room) {
        rooms.remove(room);
    }
}