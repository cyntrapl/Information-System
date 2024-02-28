import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileOpener {
    FileReader openFile(String name) throws FileNotFoundException {
        return new FileReader(new File(name)); // this doesn't work lmfao
    }
}
