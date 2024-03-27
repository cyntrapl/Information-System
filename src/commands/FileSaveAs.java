package commands;

import singletons.CurrentFile;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileSaveAs implements FileCommands{
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
        currentFile.setCurrentFileName(null);
        String temp = String.valueOf(currentFile.getFileContent());
        fileOpen.execute();
        currentFile.setFileContent(new StringBuilder(temp));
        fileSave.execute();
    }
}
