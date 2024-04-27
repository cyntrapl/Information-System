package file_commands;

import interfaces.Command;
import singletons.CurrentFile;

import java.io.IOException;

public class FileClose implements Command {
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
