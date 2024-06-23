package commands.file_commands;

import commands.CommandClass;

import java.util.Scanner;

/**
 * Класът FileSaveAs отговаря за записването на данните на текущия файл в нов файл.
 * Той разширява класа CommandClass и пренаписва метода execute, за да извърши операцията по запазване на файла.
 */
public class FileSaveAs extends CommandClass {
    private FileSave fileSave;
    private FileOpen fileOpen;

    /**
     * Конструира нов обект FileSaveAs с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public FileSaveAs(Scanner scanner) {
        super(scanner);
        this.fileSave = new FileSave(scanner);
        this.fileOpen = new FileOpen(scanner);
    }

    /**
     * Изпълнява операцията за запазване на файла.
     * Този метод отговаря за съхраняването на оригиналното съдържание на файла, като задава нулево име на текущия файл,
     * опит за отваряне на нов файл и ако отварянето на новия файл е успешно, записва оригиналното съдържание в новия файл.
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
            fileSave.execute();
        }
    }
}
