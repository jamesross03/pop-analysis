package org.example;
import java.io.FileNotFoundException;

public class Analysis {
    //TODO: Replace with a constant in a constants file rather than magic String
    private static final String RECORD_FORMAT = "TD";

    public static void main(String[] args) throws Exception {
        Parser.getAllLines("src/main/resources/inputs/synthetic_data-3k/birth_records.csv");
    }
}
