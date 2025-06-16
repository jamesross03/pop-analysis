package org.example;

/**
 * Defines constants used throughout the program (e.g acceptable configuration 
 * values).
 */
public class Constants {
    // ---- Record formats ----
    public static final String TD = "TD";
    /** List of permitted record formats */
    public static final String[] FORMATS = new String[]{TD};

    // ---- Record types ----
    public static final String BIRTH = "BIRTH";
    /** List of permitted record types */
    public static final String[] TYPES = new String[]{BIRTH};

    // ---- Analysis options ----
    public static final String FORENAME_FREQ = "FORENAME_FREQ";
    public static final String SURNAME_FREQ = "SURNAME_FREQ";
    /** List of permitted analysis operations */
    public static final String[] ANALYSIS_OPS = new String[]{FORENAME_FREQ, SURNAME_FREQ};

    // ---- Config files ----
    public static final String RECORDS_FILEPATH_KEY = "records_location";
    public static final String RECORDS_FORMAT_KEY = "record_format";
    public static final String RECORDS_TYPE_KEY = "records_type";
    public static final String ANALYSIS_TYPE_KEY = "analysis";
    public static final String RESULTS_FILEPATH_KEY = "results_save_location";
    /** Comment-indicator for config file */
    public static final String COMMENT_INDICATOR = "#";
}
