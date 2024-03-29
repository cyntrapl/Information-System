package main;

import commands.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        FileOpen fileOpen = new FileOpen(scanner);
        HelpDisplay helpDisplay = new HelpDisplay();
        FileClose fileClose = new FileClose();
        FileSave fileSave = new FileSave();
        FileSaveAs fileSaveAs = new FileSaveAs(scanner);


        Map<String, Runnable> commands = new HashMap<>();
        commands.put("open", () -> {
            if (scanner.hasNext()) {
                try {
                    fileOpen.execute();
                } catch (IOException e) {
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
            }catch (IOException e){
                System.out.println("Error closing file: " + e);
            }
        });
        commands.put("save", () -> {
            try{
                fileSave.execute();
            }catch (IOException e){
                System.out.println("Error saving file: " + e);
            }
        });
        commands.put("saveas", () -> {
            try{
                fileSaveAs.execute();
            }catch (IOException e){
                System.out.println("Error saving file: " + e);
            }
        });
        commands.put("exit", () -> System.exit(0));


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