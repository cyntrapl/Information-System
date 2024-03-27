package commands;

import singletons.CurrentFile;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;

public class FileSave implements FileCommands {
    private CurrentFile currentFile;

    public FileSave() {
        this.currentFile = CurrentFile.getInstance();
    }

    @Override
    public void execute() throws IOException {
        if (currentFile.getCurrentFileName() == null) {
            System.out.println("No file is currently open.");
            return;
        }
        FileWriter writer = new FileWriter(currentFile.getCurrentFileName());
        writer.write(currentFile.getFileContent().toString());
        writer.close();
        System.out.println("Successfully saved " + currentFile.getCurrentFileName());
    }
}
