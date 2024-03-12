import java.io.*;

public class FileOpener {
    RandomAccessFile openFile(String name) throws IOException {
        return new RandomAccessFile(name, "rw"); // this "works"
    }
}
