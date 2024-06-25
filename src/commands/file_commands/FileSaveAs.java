package commands.file_commands;

import commands.CommandClass;

import java.util.Scanner;

/**
 * Command to save the current file as a new file
 */
public class FileSaveAs extends CommandClass {
    private FileSave fileSave;
    private FileOpen fileOpen;

    /**
     * Constructor for FileSaveAs
     * @param scanner the scanner to read input
     */
    public FileSaveAs(Scanner scanner) {
        super(scanner);
        this.fileSave = new FileSave(scanner);
        this.fileOpen = new FileOpen(scanner);
    }

    /**
     * Executes the command to save the current file as a new file
     */
    @Override
    public void execute() {
        // Store the original file name and content
        //String originalFileName = getCurrentFile().getCurrentFileName();
        StringBuilder originalContent = new StringBuilder(getCurrentFile().getFileContent());

        // Set the current file name to null
        getCurrentFile().setCurrentFileName(null);

        // Try to open a new file
        fileOpen.execute();

        // If opening the new file succeeds, save the content to the new file
        if (fileOpen.isFileOpen()) {
            getCurrentFile().setFileContent(new StringBuilder(originalContent));
            System.out.println("Press enter to continue");
            fileSave.execute();
        }
    }
}
