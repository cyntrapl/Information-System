package commands;

public class HelpDisplay implements BaseCommand {
    @Override
    public void execute(){
        System.out.println("open  <file>            | opens <file>");
        System.out.println("close                   | closes currently opened file");
        System.out.println("save                    | saves the currently open file");
        System.out.println("saveas <file>           | saves the currently open file in <file>");
        System.out.println("help                    | prints this information");
        System.out.println("exit                    | exits the program");
    }
}
