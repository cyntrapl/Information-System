import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu {
    private void printHelp(){
        System.out.println("open <file> | opens <file>\n" +
                "close | closes currently opened file\n" +
                "save | saves the currently open file\n" +
                "saveas <file> | saves the currently open file in <file>\n" +
                "help | prints this information\n" +
                "exit | exists the program");
    }

    public void init() {
        printHelp();
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();
        String command, argument;

        do {
            command = scanner.next();
            switch(command){
                case "open":
                    FileOpener fileOpener = new FileOpener();
                    argument = scanner.next();
                    try{
                        fileManager.setFile(fileOpener.openFile(argument));
                    }catch (FileNotFoundException e){
                        System.out.println("Bozo");
                    }
                    System.out.println(argument);
                    break;
                case "close":

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
