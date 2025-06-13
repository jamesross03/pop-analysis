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
}
