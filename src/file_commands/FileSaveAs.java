package file_commands;

import interfaces.Command;
import singletons.CurrentFile;

import java.io.IOException;
import java.util.Scanner;

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
    public void execute() throws IOException {
        //TODO: if open doesn't work I need this to still preserve the old file instead of just outright closing it
        currentFile.setCurrentFileName(null);
        String temp = String.valueOf(currentFile.getFileContent());
        fileOpen.execute();
        currentFile.setFileContent(new StringBuilder(temp));
        fileSave.execute();
    }
}
