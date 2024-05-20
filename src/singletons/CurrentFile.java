package singletons;

/**
 * handles the currently open file
 */
public class CurrentFile{
    private static volatile CurrentFile instance;

    private String currentFileName;
    private StringBuilder fileContent;

    private CurrentFile() {
        this.currentFileName = null;
        this.fileContent = new StringBuilder();
    }

    public static CurrentFile getInstance() {

        CurrentFile result = instance;
        if (result != null) {
            return result;
        }
        synchronized(CurrentFile.class) {
            if (instance == null) {
                instance = new CurrentFile();
            }
            return instance;
        }
    }

    public String getCurrentFileName() {
        return currentFileName;
    }

    public StringBuilder getFileContent() {
        return fileContent;
    }

    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
    }

    public void setFileContent(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }
}
