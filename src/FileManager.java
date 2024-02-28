import java.io.File;
import java.io.FileReader;

public class FileManager {
    private FileReader file;

    public FileReader getFile() {
        return file;
    }

    public void setFile(FileReader file) {
        this.file = file;
    }
}
