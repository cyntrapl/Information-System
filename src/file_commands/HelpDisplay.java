package file_commands;

import interfaces.Command;

public class HelpDisplay implements Command {
    @Override
    public void execute(){
        System.out.println("===============================file commands: ");
        System.out.println("open  <file>                                          | opens <file>");
        System.out.println("close                                                 | closes currently opened file");
        System.out.println("save                                                  | saves the currently open file");
        System.out.println("saveas <file>                                         | saves the currently open file in <file>");
        System.out.println("help                                                  | prints this information");
        System.out.println("exit                                                  | exits the program");
        System.out.println("==============================hotel commands: ");
        System.out.println("checkin <room> <from> <to> <note> [<guests>]          | checks in at room, guests=bed if empty");
        System.out.println("availability [<date>]                                 | prints all available rooms at date, if date empty it will be today's date");
        System.out.println("checkout <room>                                       | checks out room");
        System.out.println("report <from> <to>                                    | reports the usage of rooms in the specified period");
        System.out.println("find <beds> <from> <to>                               | finds a free room");
        System.out.println("find! <beds> <from> <to>                              | finds a free room allowing for swaps and wanting the exact number of beds");
        System.out.println("unavailable <room> <from> <to>                        | make room unavailable");
        System.out.println("activities <no arguments>                             | finds all rooms with selected activity from pop up menu");


    }
}
