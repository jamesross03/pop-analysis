package org.example;

import java.util.List;

public class Analysis {
    //TODO: Replace with a constant in a constants file rather than magic String
    private static final String RECORD_FORMAT = "TD";
    private static final String RECORD_TYPE = "BIRTH";

    public static void main(String[] args) throws Exception {
        // TODO: Add error handling for missing arguments
        List<Person> people = Parser.getAllLines(args[0], RECORD_FORMAT, RECORD_TYPE);
        System.out.println("Read " + people.size() + " people");
    }
}
