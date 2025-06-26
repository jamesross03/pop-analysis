package org.example;

import java.util.Map;

import com.github.jamesross03.pop_parser.utils.Record;
import com.github.jamesross03.pop_parser.utils.RecordFormat;
import com.github.jamesross03.pop_parser.utils.formats.TDFormat;
import com.github.jamesross03.pop_parser.utils.records.BirthRecord;

/**
 * Defines constants used throughout the program (e.g acceptable configuration 
 * values).
 */
public class Constants {
    // ---- Record formats ---- 
    public static final Map<String, RecordFormat> FORMATS_MAP = Map.of(
        "TD", new TDFormat()
    );

    // ---- Record types ----
    public static final Map<String, Class<? extends Record>> TYPES_MAP = Map.of(
        "BIRTH", BirthRecord.class
    );

    // ---- Analysis options ----
    public static final String FORENAME_FREQ = "FORENAME_FREQ";
    public static final String SURNAME_FREQ = "SURNAME_FREQ";
    /** List of permitted analysis operations */
    public static final String[] ANALYSIS_OPS = new String[]{FORENAME_FREQ, SURNAME_FREQ};

    // ---- Config files ----
    public static final String RECORDS_FILEPATH_KEY = "records_location";
    public static final String RECORD_FORMAT_KEY = "record_format";
    public static final String RECORD_TYPE_KEY = "record_type";
    public static final String ANALYSIS_TYPE_KEY = "analysis";
    public static final String RESULTS_FILEPATH_KEY = "results_save_location";
    public static final String PURPOSE_KEY = "purpose";
    /** Comment-indicator for config file */
    public static final String COMMENT_INDICATOR = "#";
}
