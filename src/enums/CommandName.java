package enums;

public enum CommandName {
    OPEN("open"),
    CLOSE("close"),
    SAVE("save"),
    SAVEAS("saveas"),
    CHECKIN("checkin"),
    CHECKOUT("checkout"),
    AVAILABILITY("availability"),
    REPORT("report"),
    UNAVAILABLE("unavailable"),
    FIND("find"),
    FINDSWAP("find!"),
    ACTIVITIES("activities"),
    HELP("help"),
    EXIT("exit");

    private final String textValue;

    CommandName(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return this.textValue;
    }

    public static CommandName fromTextValue(String textValue) {
        for (CommandName commandName : CommandName.values()) {
            if (commandName.getTextValue().equals(textValue)) {
                return commandName;
            }
        }
        throw new IllegalArgumentException("No enum constant " + CommandName.class.getCanonicalName() + " with text value " + textValue);
    }
}