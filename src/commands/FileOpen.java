package commands;

import singletons.CurrentFile;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileOpen implements FileCommands{
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
            while (fileScanner.hasNextLine()) {
               currentFile.getFileContent().append(fileScanner.nextLine()).append("\n");
            }
            currentFile.setCurrentFileName(fileName);
            System.out.println("File opened: " + fileName);

        }else {
            System.out.println("a file is already open!");
            scanner.next();
        }

    }
}
