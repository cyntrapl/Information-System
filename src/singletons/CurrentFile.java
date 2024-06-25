package singletons;

/**
 * Singleton class for the current file
 */
public class CurrentFile{
    private static volatile CurrentFile instance;
    private String currentFileName;
    private StringBuilder fileContent;

    /**
     * Constructor for the CurrentFile class
     */
    private CurrentFile() {
        this.currentFileName = null;
        this.fileContent = new StringBuilder();
    }

    /**
     * Gets the instance of the CurrentFile class
     * @return the instance of the CurrentFile class
     */
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


    /**
     * Gets the current file name
     * @return the current file name
     */
    public String getCurrentFileName() {
        return currentFileName;
    }

    /**
     * Gets the content of the file
     * @return the content of the file
     */
    public StringBuilder getFileContent() {
        return fileContent;
    }

    /**
     * Sets the current file name
     * @param currentFileName the current file name
     */
    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
    }

    /**
     * Sets the content of the file
     * @param fileContent the content of the file
     */
    public void setFileContent(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }
}
