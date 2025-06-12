package org.example;

import java.util.Map;

public class PersonFactory {
    //TODO: Make this an astract class and make different implementations for different record types
    //TODO: Can the Valipop person and parternships classes?
    private final String FORMAT;
    private final String TYPE;

    public PersonFactory(String format, String type) {
        this.FORMAT = format;
        this.TYPE = type;
    }

    public Person makePerson(Map<String, String> entry) {
        // TODO: Replace magic strings
        return switch(FORMAT) {
            case "DS" -> switch(TYPE) {
                case "BIRTH" -> new Person(
                    entry.get("child's forname(s)"),
                    entry.get("child's surname")
                );
                default -> null;
            };
            default -> null;
        } ;
    }
}
