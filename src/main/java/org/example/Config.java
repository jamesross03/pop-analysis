package org.example;

/**
 * Contains configuration options for the pop-analysis program. Default options
 * can be modified within this class and other attributes can be set at runtime
 * using a configuration file. 
 */
public class Config {
    // TODO: Include defaults as static here
    public final String recordFormat = Constants.TD;
    public final String recordType = Constants.BIRTH;
    // TODO: Make this some form of list to perform multiple operations
    public final String analysisType = "SURNAME_FREQ";
    public final String resultsDir = "results";
    public final String recordsFilepath = "src/main/resources/inputs/synthetic_3k/birth_records.csv";
}
