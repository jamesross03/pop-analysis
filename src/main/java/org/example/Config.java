package org.example;

public class Config {
    // TODO: Include defaults as static here
    public final String recordFormat = Constants.TD;
    public final String recordType = Constants.BIRTH;
    // TODO: Make this some form of list to perform multiple operations
    public final String analysisType = "SURNAME_FREQ";
    public final String resultsDir = "results";
    public final String recordsFilepath = "src/main/resources/inputs/synthetic_3k/birth_records.csv";
}
