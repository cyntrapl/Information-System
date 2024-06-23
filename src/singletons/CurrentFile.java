package singletons;

/**
 * Класът CurrentFile е сингълтън клас, който съдържа информация за текущо отворения файл.
 */
public class CurrentFile{
    private static volatile CurrentFile instance;

    private String currentFileName;
    private StringBuilder fileContent;

    /**
     * Създава нов обект CurrentFile.
     */
    private CurrentFile() {
        this.currentFileName = null;
        this.fileContent = new StringBuilder();
    }

    /**
     * Връща единствения обект на класа.
     * @return обекта на класа
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
     * Връща името на текущия файл.
     * @return името на текущия файл
     */
    public String getCurrentFileName() {
        return currentFileName;
    }

    /**
     * Връща съдържанието на текущия файл.
     * @return съдържанието на текущия файл
     */
    public StringBuilder getFileContent() {
        return fileContent;
    }

    /**
     * Задава името на текущия файл.
     * @param currentFileName името на текущия файл
     */
    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
    }

    /**
     * Задава съдържанието на текущия файл.
     * @param fileContent съдържанието на текущия файл
     */
    public void setFileContent(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }
}
