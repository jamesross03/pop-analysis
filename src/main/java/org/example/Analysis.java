package org.example;

import java.util.List;

public class Analysis {
    //TODO: Replace with a constant in a constants file rather than magic String
    private static final String RECORD_FORMAT = "TD";
    private static final String RECORD_TYPE = "BIRTH";
    // TODO: Make this some form of list to perform multiple operations
    private static final String ANALYSIS_TYPE = "SURNAME_FREQ";

    public static void main(String[] args) throws Exception {
        // TODO: Add error handling for missing arguments
        List<Person> people = Parser.getAllLines(args[0], RECORD_FORMAT, RECORD_TYPE);
        FreqTable table = new FreqTable(ANALYSIS_TYPE);
        table.addPeople(people);
        // TODO: Handle creating results directory if not already exists
        table.outputTable("results/table.csv");
    }
}
