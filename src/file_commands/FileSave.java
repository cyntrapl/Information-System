package file_commands;

import interfaces.Command;
import singletons.CurrentFile;

import java.io.FileWriter;
import java.io.IOException;

public class FileSave implements Command {
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
