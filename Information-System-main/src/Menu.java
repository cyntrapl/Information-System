import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private void printHelp(){
        System.out.println("" +
                "open <file>   | opens <file>\n" +
                "close         | closes currently opened file\n" +
                "save          | saves the currently open file\n" +
                "saveas <file> | saves the currently open file in <file>\n" +
                "help          | prints this information\n" +
                "exit          | exists the program");
    }

    public void init() {
        Scanner scanner = new Scanner(System.in);
        printHelp();
        String command, argument, fileData;
        FileManager fileManager = new FileManager();

        do {
            command = scanner.next();
            switch(command){
                case "open":
                    argument = scanner.next();
                    if(fileManager.getFile() == null){
                        FileOpener fileOpener = new FileOpener();
                        try{
                            fileManager.setFile(fileOpener.openFile(argument));
                            fileData = fileManager.readData();
                        }catch (IOException e){
                            System.out.println("Error opening file!");
                        }
                    }else System.out.println("File already open");
                    break;
                case "close":
                    FileCloser fileCloser = new FileCloser();
                    if(fileManager.getFile() != null){
                        try{
                            fileCloser.closeFile(fileManager.getFile());
                            fileManager.setFile(null);
                        }catch(IOException e){ System.out.println("Error closing file!"); }
                    }else System.out.println("File not open");
                    break;
                case "save":

                    break;
                case "saveas":

                    break;
                case "help":
                    printHelp();
                    break;
                default:
                    System.out.println("Invalid argument");
            }
        } while (!command.equalsIgnoreCase("exit"));
    }
}
