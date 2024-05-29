package file_commands;

import exceptions.FileNotOpenException;
import interfaces.Command;
import singletons.CurrentFile;

import java.io.FileWriter;
import java.io.IOException;

/**
 * saves currently open file
 */
public class FileSave implements Command {
    private CurrentFile currentFile;

    public FileSave() {
        this.currentFile = CurrentFile.getInstance();
    }

    @Override
    public void execute() throws IOException, FileNotOpenException {
        if (currentFile.getCurrentFileName() == null) {
            throw new FileNotOpenException("No file is currently open.");
        }
        FileWriter writer = new FileWriter(currentFile.getCurrentFileName());
        writer.write(currentFile.getFileContent().toString());
        writer.close();
        System.out.println("Successfully saved " + currentFile.getCurrentFileName());
    }
}
