package commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;

public interface FileCommands extends Command{
    void execute() throws IOException;
}
