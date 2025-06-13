package org.example;

import java.util.Map;

public class PersonFactory {
    //TODO: Make this an astract class and make different implementations for different record types
    //TODO: Can we use the Valipop person and parternships classes?
    private final String format;
    private final String type;

    public PersonFactory(String format, String type) {
        this.format = format;
        this.type = type;
    }

    public Person makePerson(Map<String, String> entry) {
        // TODO: Replace magic strings
        return switch(format) {
            case "TD" -> switch(type) {
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
