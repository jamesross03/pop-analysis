package org.example;

import java.util.Map;

/**
 * Factory class for making Record objects from a line of CSV input. 
 */
public class PersonFactory {
    //TODO: Make this an astract class and make different implementations for different record types
    //TODO: Can we use the Valipop person and parternships classes?
    private final String format;
    private final String type;

    public PersonFactory(String format, String type) {
        this.format = format;
        this.type = type;
    }

    /**
     * @param entry Map of <header, value> pairs
     * @return generated Record
     */
    public Person makeRecord(Map<String, String> entry) {
        return switch(format) {
            case Constants.TD -> switch(type) {
                case Constants.BIRTH -> new Person(
                    entry.get("child's forname(s)"),
                    entry.get("child's surname")
                );
                default -> null;
            };
            default -> null;
        } ;
    }
}
