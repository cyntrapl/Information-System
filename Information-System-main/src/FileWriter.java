import java.io.IOException;
import java.io.RandomAccessFile;

interface FileWriter {
    void saveData(String data, RandomAccessFile file) throws IOException;
}
