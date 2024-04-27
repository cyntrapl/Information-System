package file_commands;

import interfaces.Command;
import singletons.CurrentFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileOpen implements Command {
    private CurrentFile currentFile;
    private Scanner scanner;

    public FileOpen(Scanner scanner) {
        this.currentFile = CurrentFile.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() throws IOException {
        if(currentFile.getCurrentFileName() == null){
            String fileName = scanner.next();
            if(Objects.equals(fileName, "rooms.txt")){
                System.out.println("Don't open this file!!");
                return;
            }
            Pattern pattern = Pattern.compile(".*\\.txt$");
            Matcher matcher = pattern.matcher(fileName);
            if(!matcher.matches()){
                System.out.println("Not a valid file (ex name.txt)");
                return;
            }
            File file = new File(fileName);

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    System.out.println("Error: Unable to create the file.");
                    return;
                }
                System.out.println("File created and opened: " + fileName);
            }

            Scanner fileScanner = new Scanner(file);

            currentFile.getFileContent().setLength(0);
            while (fileScanner.hasNextLine())
               currentFile.getFileContent().append(fileScanner.nextLine()).append("\n");
            currentFile.setCurrentFileName(fileName);
            System.out.println("File opened: " + fileName);
        }else {
            System.out.println("a file is already open!");
            scanner.next();
        }

    }
}
