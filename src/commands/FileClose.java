package commands;

import singletons.CurrentFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;

public class FileClose implements FileCommands {
    private CurrentFile currentFile;


    public FileClose() {
        this.currentFile = CurrentFile.getInstance();
    }

    @Override
    public void execute() throws IOException {
        if(currentFile.getCurrentFileName() == null) System.out.println("No file is open!");
        else {
            currentFile.setCurrentFileName(null);
            currentFile.getFileContent().setLength(0);
        }
    }
}
