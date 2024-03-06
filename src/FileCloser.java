import java.io.IOException;
import java.io.RandomAccessFile;

public class FileCloser {
    public void closeFile(RandomAccessFile file) throws IOException {
        file.close();
    }
}
