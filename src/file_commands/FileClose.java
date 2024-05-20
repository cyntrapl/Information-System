package file_commands;

import interfaces.Command;
import singletons.CurrentFile;
import singletons.Hotel;

import java.io.IOException;

/**
 * closes file
 */
public class FileClose implements Command {
    private CurrentFile currentFile;
    private Hotel hotel;


    public FileClose() {
        this.currentFile = CurrentFile.getInstance();
        this.hotel = Hotel.getInstance();
    }

    @Override
    public void execute() throws IOException {
        if(currentFile.getCurrentFileName() == null) System.out.println("No file is open!");
        else {
            currentFile.setCurrentFileName(null);
            currentFile.getFileContent().setLength(0);
            hotel.clearAllRooms();
            System.out.println("File has been closed!");
        }
    }
}
