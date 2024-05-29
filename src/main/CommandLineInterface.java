package main;

import enums.Activities;
import exceptions.FileNotOpenException;
import exceptions.InvalidFileNameException;
import exceptions.InvalidNumberOfArgumentsException;
import file_commands.*;
import hotel_commands.*;
import singletons.Hotel;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * responsible for the console handling
 */
public class CommandLineInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        FileOpen fileOpen = new FileOpen(scanner);
        HelpDisplay helpDisplay = new HelpDisplay();
        FileClose fileClose = new FileClose();
        FileSave fileSave = new FileSave();
        FileSaveAs fileSaveAs = new FileSaveAs(scanner);
        CheckIn checkin = new CheckIn(scanner);
        Checkout checkout = new Checkout(scanner);
        Availability availability = new Availability(scanner);
        Report report = new Report(scanner);
        Unavailable unavailable = new Unavailable(scanner);
        Find find = new Find(scanner);
        FindImportant findImportant = new FindImportant(scanner);
        ActivityList activityList = new ActivityList(scanner);
        Hotel hotel = Hotel.getInstance();
        hotel.getNumberOfRooms();

        //TODO:put this in a different class so it's easier to add commands on the eye instead of this mess
        Map<String, Runnable> commands = new HashMap<>();
        commands.put("open", () -> {
            if (scanner.hasNext()) {
                try {
                    fileOpen.execute();
                } catch (IOException | InvalidFileNameException e){
                    System.out.println("Error opening file: " + e);
                }
            } else {
                System.out.println("Usage: open <file>");
            }
        });
        commands.put("help", helpDisplay::execute);
        commands.put("close", () -> {
            try{
                fileClose.execute();
            }catch (IOException | FileNotOpenException e){
                System.out.println("Error closing file: " + e);
            }
        });
        commands.put("save", () -> {
            try{
                fileSave.execute();
            }catch (IOException | FileNotOpenException e){
                System.out.println("Error saving file: " + e);
            }
        });
        commands.put("saveas", () -> {
            try{
                fileSaveAs.execute();
            }catch (IOException | FileNotOpenException | InvalidFileNameException e){
                System.out.println("Error saving file: " + e);
            }
        });
        commands.put("checkin", () -> {
            try{
                checkin.execute();
            }catch (InvalidNumberOfArgumentsException | FileNotOpenException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("checkout", () -> {
            try{
                checkout.execute();
            }catch (InvalidNumberOfArgumentsException | FileNotOpenException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("availability", () -> {
            try{
                availability.execute();
            }catch (InvalidNumberOfArgumentsException | FileNotOpenException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("report", () -> {
            try{
                report.execute();
            }catch (InvalidNumberOfArgumentsException | FileNotOpenException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("unavailable", () -> {
            try{
                unavailable.execute();
            }catch (InvalidNumberOfArgumentsException | FileNotOpenException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("find", () -> {
            try{
                find.execute();
            }catch (InvalidNumberOfArgumentsException | FileNotOpenException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("find!", () -> {
            try{
                findImportant.execute();
            }catch (InvalidNumberOfArgumentsException | FileNotOpenException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("activities", () -> {
            try{
                findImportant.execute();
            }catch (FileNotOpenException | InvalidNumberOfArgumentsException e){
                System.out.println("Error: " + e);
            }
        });
        commands.put("exit", () -> System.exit(0));

        //display help at start
        helpDisplay.execute();
        while (true) {
            System.out.print("> ");
            if (scanner.hasNext()) {
                command = scanner.next().toLowerCase();
                if (commands.containsKey(command)) {
                    commands.get(command).run();
                } else {
                    System.out.println("Invalid command. Type 'help' to see available commands.");
                }
            }
        }

    }
}