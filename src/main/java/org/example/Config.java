package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Contains configuration options for the pop-analysis program. Default options
 * can be modified within this class and other attributes can be set at runtime
 * using a configuration file. 
 */
public class Config {
    // ---- Default values ----
    private static final String DEFAULT_RESULTS_DIR = "results";
    private static final String DEFAULT_ANALYSIS_TYPE = Constants.SURNAME_FREQ;
    
    // ---- Required Parameters ----
    private String recordsFilepath;
    private String recordType;
    private String recordFormat;

    // ---- Optional Parameters ----
    // TODO: Make this some form of list to perform multiple operations
    private String analysisType = DEFAULT_ANALYSIS_TYPE;
    private String resultsDir = DEFAULT_RESULTS_DIR;

    public Config(String recordsFilepath, String recordType, String recordFormat) {
        this.recordsFilepath = recordsFilepath;
        this.recordType = recordType;
        this.recordFormat = recordFormat;
        
        validateArgs();
    }

    public Config(String configFilepath) throws FileNotFoundException, IOException {
        Map<String, BiConsumer<Config, String>> processors = getProcessors();
        List<String> lines = ConfigFileParser.getAllLines(configFilepath);
        processArgs(lines, processors);
        validateArgs();
    }

    private Map<String, BiConsumer<Config, String>> getProcessors() {
        Map<String, BiConsumer<Config, String>> processors = 
            new HashMap<String, BiConsumer<Config, String>>();

        processors.put(Constants.RECORDS_FILEPATH_KEY, Config::setRecordsFilepath);
        processors.put(Constants.RECORDS_FORMAT_KEY, Config::setRecordFormat);
        processors.put(Constants.RECORDS_TYPE_KEY, Config::setRecordType);
        processors.put(Constants.ANALYSIS_TYPE_KEY, Config::setAnalysisType);
        processors.put(Constants.RESULTS_FILEPATH_KEY, Config::setResultsDir);

        return processors;
        
    }
    
    private void processArgs(List<String> lines, Map<String, BiConsumer<Config, String>> processors) {
        String[] parts;
        String value;

        for (String ln : lines) {
            parts = ln.split("=");
            value = String.join("=", Arrays.copyOfRange(parts, 1, parts.length)).trim();
            processors.get(parts[0].trim()).accept(this, value);
            // TODO: Catch invalid args
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

    public String getResultsDir() {
        return resultsDir;
    }

    public void setResultsDir(String resultsDir) {
        this.resultsDir = resultsDir;
    }
}
