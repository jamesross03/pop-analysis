package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.example.utils.ConfigFileParser;

import com.github.jamesross03.pop_parser.utils.Record;
import com.github.jamesross03.pop_parser.utils.RecordFormat;

/**
 * Contains configuration options for the pop-analysis program. Default options
 * can be modified within this class and other attributes can be set at runtime
 * using a configuration file. 
 */
public class Config {
    // ---- Default values ----
    private static final String DEFAULT_RESULTS_DIR = "results";
    private static final String DEFAULT_ANALYSIS_TYPE = Constants.SURNAME_FREQ;
    private static final String DEFAULT_PURPOSE = "default";
    
    // ---- Required Parameters ----
    /** Filepath to read records csv from */
    private String recordsFilepath;
    /** Type of record, e.g Birth, Marriage, Death */
    private Class<? extends Record> recordType;
    /** Record format, e.g DS or TD */
    private RecordFormat recordFormat;

    // ---- Optional Parameters ----
    // TODO: Make this some form of list to perform multiple operations
    /** Analysis operation to perform on record-data */
    private String analysisType = DEFAULT_ANALYSIS_TYPE;
    /** Results directory to store output in */
    private String resultsDir = DEFAULT_RESULTS_DIR;
    /** Purpose of run (used for categorising results in results dir) */
    private String purpose = DEFAULT_PURPOSE;

    // ---- Set automatically at runtime ----
    /**
     * Directory to output results to for this run. Takes structure
     * <resultsDir/<purpose>/<timestamp>
     */
    private String outputDir;

    public Config(String recordsFilepath, String recordType, String recordFormat) {
        this.recordsFilepath = recordsFilepath;
        this.recordType = Constants.TYPES_MAP.get(recordType);
        this.recordFormat = Constants.FORMATS_MAP.get(recordFormat);
        this.outputDir = generateOutputFilepath();
        
        validateArgs();
    }

    public Config(String configFilepath) throws FileNotFoundException, IOException {
        Map<String, BiConsumer<Config, String>> processors = getProcessors();
        List<String> lines = ConfigFileParser.getAllLines(configFilepath);
        processArgs(lines, processors);
        validateArgs();
        this.outputDir = generateOutputFilepath();
    }

    /**
     * Generates a Map of key-function pairs to use to process different keys
     * read-in from the config file.
     * 
     * @return Map of <key, processor-function> pairs for the config file
     */
    private Map<String, BiConsumer<Config, String>> getProcessors() {
        Map<String, BiConsumer<Config, String>> processors = 
            new HashMap<String, BiConsumer<Config, String>>();

        processors.put(Constants.RECORDS_FILEPATH_KEY, Config::setRecordsFilepath);
        processors.put(Constants.RECORD_FORMAT_KEY, Config::setRecordFormat);
        processors.put(Constants.RECORD_TYPE_KEY, Config::setRecordType);
        processors.put(Constants.ANALYSIS_TYPE_KEY, Config::setAnalysisType);
        processors.put(Constants.RESULTS_FILEPATH_KEY, Config::setResultsDir);
        processors.put(Constants.PURPOSE_KEY, Config::setPurpose);

        return processors;
        
    }
    
    /**
     * Processes lines read-in from a config-file, using processor functions
     * specified for each key.
     * 
     * @param lines
     * @param processors Map of <key, processor-function> pairs for the config
     *                   file
     */
    private void processArgs(List<String> lines, Map<String, BiConsumer<Config, String>> processors) {
        String[] parts;
        String key;
        String value;
        BiConsumer<Config, String> processor;

        for (String ln : lines) {
            parts = ln.split("=");

            if (parts.length == 1) {
                throw new IllegalArgumentException("Malformed config line: `" + ln + "`. Expected format: key=value");
            }

            key = parts[0].trim();
            if ((processor = processors.get(key)) == null) {
                throw new IllegalArgumentException("`" + key + "` is not a recognised option");
            }

            // Incase values string contains '='
            value = String.join("=", Arrays.copyOfRange(parts, 1, parts.length)).trim();
            processor.accept(this, value);
        }
    }

    /**
     * Validate arguments provided in configuration file, checks validity and
     * that all required options are set.
     * 
     * @throws IllegalArgumentException
     */
    private void validateArgs() throws IllegalArgumentException {
        if (recordsFilepath == null) {
            throw new IllegalArgumentException("`" + Constants.RECORDS_FILEPATH_KEY + "` is required");
        }
        if (recordType == null) {
            throw new IllegalArgumentException("`" + Constants.RECORD_FORMAT_KEY + "` is required");
        }
        if (recordFormat == null) {
            throw new IllegalArgumentException("`" + Constants.RECORD_TYPE_KEY + "` is required");
        }
        if (!Arrays.asList(Constants.ANALYSIS_OPS).contains(analysisType)) {
            throw new IllegalArgumentException("Unrecognised analysis type: `" + analysisType + "`");
        }
    }

    /**
     * Generates filepath to use for outputs
     * (<resultsDir/<purpose>/<timestamp>).
     * 
     * @return output dir filepath
     */
    private String generateOutputFilepath() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");

        return new StringBuilder(resultsDir)
            .append("/" + purpose)
            .append("/" + now.format(formatter))
            .append("-" + String.format("%03d", now.getNano() / 1_000_000))
            .toString();
    }

    public Class<? extends Record> getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = Constants.TYPES_MAP.get(recordType);
    }

    public String getRecordsFilepath() {
        return recordsFilepath;
    }

    public void setRecordsFilepath(String recordsFilepath) {
        this.recordsFilepath = recordsFilepath;
    }

    public RecordFormat getRecordFormat() {
        return recordFormat;
    }

    public void setRecordFormat(String recordFormat) {
        this.recordFormat = Constants.FORMATS_MAP.get(recordFormat);
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public void setResultsDir(String resultsDir) {
        this.resultsDir = resultsDir;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOutputDir() {
        return outputDir;
    }
}
