import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {
    private RandomAccessFile file;

    public RandomAccessFile getFile() {
        return file;
    }

    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

    public String readData() throws IOException {
        long fileLength = file.length();
        byte[] content = new byte[(int) fileLength];
        file.readFully(content);

        return new String(content);
    }
}
