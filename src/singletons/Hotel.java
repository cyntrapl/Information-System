package singletons;

import hotel.HotelRoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класът Hotel представлява хотел със стаи.
 */
public class Hotel {
    private static volatile Hotel instance;
    private List<HotelRoom> rooms;
    private int numberOfRooms;

    /**
     * Създава нов обект Hotel.
     */
    private Hotel() {
        rooms = new ArrayList<>();
        numberOfRooms = 0;
    }

    /**
     * Връща единствения обект на класа.
     * @return обекта на класа
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
     * Връща броя на стаите в хотела.
     * @return броя на стаите
     */
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

    /**
     * Зарежда всички стаи в хотела.
     * @param roomList списък с хотелски стаи
     */
    public void loadAllRooms(List<HotelRoom> roomList) {
        rooms.addAll(roomList);
    }

    /**
     * Изчиства всички стаи в хотела.
     */
    public void clearAllRooms() {
        rooms.clear();
    }

    /**
     * Добавя стая в хотела.
     * @param room стая
     */
    public void addRoom(HotelRoom room) {
        rooms.add(room);
    }

    /**
     * Връща списък с всички стаи в хотела.
     * @return списък с хотелски стаи
     */
    public List<HotelRoom> getRooms() {
        return rooms;
    }

    /**
     * Намира стая по номер.
     * @param roomNumber номер на стая
     * @return стая
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
     * Премахва стая по номер.
     * @param roomNumber номер на стая
     */
    public void removeRoomByRoomNumber(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
    }
}