package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.example.utils.Utils;
import org.example.utils.parsers.ConfigFileParser;

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
    private String recordsFilepath;
    private String recordType;
    private String recordFormat;

    // ---- Optional Parameters ----
    // TODO: Make this some form of list to perform multiple operations
    private String analysisType = DEFAULT_ANALYSIS_TYPE;
    private String resultsDir = DEFAULT_RESULTS_DIR;
    private String purpose = DEFAULT_PURPOSE;

    // ---- Set automatically at runtime ----
    private String outputDir;

    public Config(String recordsFilepath, String recordType, String recordFormat) {
        this.recordsFilepath = recordsFilepath;
        this.recordType = recordType;
        this.recordFormat = recordFormat;
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

    private Map<String, BiConsumer<Config, String>> getProcessors() {
        Map<String, BiConsumer<Config, String>> processors = 
            new HashMap<String, BiConsumer<Config, String>>();

        processors.put(Constants.RECORDS_FILEPATH_KEY, Config::setRecordsFilepath);
        processors.put(Constants.RECORDS_FORMAT_KEY, Config::setRecordFormat);
        processors.put(Constants.RECORDS_TYPE_KEY, Config::setRecordType);
        processors.put(Constants.ANALYSIS_TYPE_KEY, Config::setAnalysisType);
        processors.put(Constants.RESULTS_FILEPATH_KEY, Config::setResultsDir);
        processors.put(Constants.PURPOSE_KEY, Config::setPurpose);

        return processors;
        
    }
    
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
            value = String.join("=", Arrays.copyOfRange(parts, 1, parts.length)).trim();

            if ((processor = processors.get(key)) == null) {
                throw new IllegalArgumentException("`" + key + "` is not a recognised option");
            }

            processor.accept(this, value);
        }
    }

    private void validateArgs() {
        if (recordsFilepath == null) {
            throw new IllegalArgumentException("`" + Constants.RECORDS_FILEPATH_KEY + "` is required");
        }
        if (recordType == null) {
            throw new IllegalArgumentException("`" + Constants.RECORDS_FORMAT_KEY + "` is required");
        }
        if (recordFormat == null) {
            throw new IllegalArgumentException("`" + Constants.RECORDS_TYPE_KEY + "` is required");
        }
        if (!Arrays.asList(Constants.TYPES).contains(recordType)) {
            throw new IllegalArgumentException("Unrecognised record type: `" + recordType + "`");
        }
        if (!Arrays.asList(Constants.FORMATS).contains(recordFormat)) {
            throw new IllegalArgumentException("Unrecognised record format: `" + recordFormat + "`");
        }
        if (!Arrays.asList(Constants.ANALYSIS_OPS).contains(analysisType)) {
            throw new IllegalArgumentException("Unrecognised analysis type: `" + analysisType + "`");
        }
    }

    private String generateOutputFilepath() {
        return new StringBuilder(resultsDir)
        .append("/" + purpose)
        .append("/" + Utils.getDateTimeString())
        .toString();
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getRecordsFilepath() {
        return recordsFilepath;
    }

    public void setRecordsFilepath(String recordsFilepath) {
        this.recordsFilepath = recordsFilepath;
    }

    public String getRecordFormat() {
        return recordFormat;
    }

    public void setRecordFormat(String recordFormat) {
        this.recordFormat = recordFormat;
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
