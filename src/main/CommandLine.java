package main;

/**
 * Main class for the command line
 */
public class CommandLine {
    /**
     * Main method for the command line
     * @param args the arguments
     */
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        invoker.invoke();
    }
}