package file_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidFileNameException;
import interfaces.Command;
import singletons.CurrentFile;

import java.io.IOException;
import java.util.Scanner;

/**
 * saves file data in a new file
 */
public class FileSaveAs implements Command {
    private CurrentFile currentFile;
    private FileSave fileSave;
    private FileOpen fileOpen;
    public FileSaveAs(Scanner scanner) {
        this.currentFile = CurrentFile.getInstance();
        this.fileSave = new FileSave();
        this.fileOpen = new FileOpen(scanner);
    }

    @Override
    public void execute() throws IOException, FileNotOpenException, InvalidFileNameException {
        //TODO: if open doesn't work I need this to still preserve the old file instead of just outright closing it
        currentFile.setCurrentFileName(null);
        String temp = String.valueOf(currentFile.getFileContent());
        try{
            fileOpen.execute();
        }catch (IOException | InvalidFileNameException e){
            System.out.println("Error: " + e);
        }
        currentFile.setFileContent(new StringBuilder(temp));
        try{
            fileSave.execute();
        }catch (IOException | FileNotOpenException e){
            System.out.println("Error: " + e);
        }
    }
}
