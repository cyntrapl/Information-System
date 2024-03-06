import java.io.IOException;
import java.io.RandomAccessFile;

public class FileSave implements FileWriter{
    @Override
    public void saveData(String data, RandomAccessFile file) throws IOException {
        file.writeChars(data);
    }
}
