package org.example.analysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.github.jamesross03.pop_parser.utils.Record;
import com.github.jamesross03.pop_parser.utils.records.BirthRecord;
import com.opencsv.CSVWriter;

import org.example.utils.Utils;
import org.example.Config;
import org.example.Constants;

import uk.ac.standrews.cs.utilities.TimeManipulation;
import uk.ac.standrews.cs.utilities.archive.Diagnostic;

/**
 * Generates a table of frequency values for a field in a record, where T is
 * the Record class (e.g BirthRecord).
 */
public class FreqTable <T extends Record>{
    /**
     * Stores <value, n occurences> pairs.
     */
    private final Map<String, Integer> counts;
    
    /**
     * Get function (static) for target field in record.
     */
    private final Function<Record, String> keyExtractor;

    /**
     * Label for target field.
     */
    private final String label;

    /**
     * Filepath to output results to.
     */
    private final String resultsFilepath;

    /**
     * @param config Config object (defines analysis type and output location)
     */
    public FreqTable(Config config) {
        counts = new HashMap<>();
        
        String type = config.getAnalysisType();
        String recordType = config.getRecordType().getSimpleName();
        switch (type) {
            case Constants.FORENAME_FREQ -> {
                keyExtractor = switch (recordType) {
                    case "BirthRecord" -> r -> ((BirthRecord) r).getForename();
                    default -> throw new IllegalArgumentException("Unsupported record type: " + recordType);
                };
                label = "forename";
            }
            case Constants.SURNAME_FREQ -> {
                keyExtractor = switch (recordType) {
                    case "BirthRecord" -> r -> ((BirthRecord) r).getSurname();
                    default -> throw new IllegalArgumentException("Unsupported record type: " + recordType);

                };
                label = "surname";
            }
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };

        resultsFilepath = new StringBuilder(config.getOutputDir())
            .append("/tables/")
            .append(label +"_freq.csv")
            .toString(); 
    }

    /**
     * Adds a List of records to the table.
     * 
     * @param records
     */
    public void add(List<T> records) {
        final long START_TIME = System.currentTimeMillis();
        Diagnostic.traceNoSource("Adding records to "+label+" frequency table");

        for (Record r : records) {
            String key = keyExtractor.apply(r);
            // Increments count by 1 or default to 1 if no value yet
            counts.merge(key, 1, Integer::sum);
        }

        TimeManipulation.reportElapsedTime(START_TIME);
    }

    /**
     * Outputs table to "<results_dir>/tables/<label>_freq.csv".
     * 
     * @throws IOException
     */
    public void output() throws IOException {
        final long START_TIME = System.currentTimeMillis();

        try (CSVWriter writer = new CSVWriter(new FileWriter(resultsFilepath))) {
            Diagnostic.traceNoSource("Outputting " + label + " frequency table");
            writer.writeNext(new String[]{Utils.capitalise(label), "Occurences"});
            
            counts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                    writer.writeNext(new String[]{entry.getKey(), entry.getValue().toString()})
                );
        }

        TimeManipulation.reportElapsedTime(START_TIME);
    }
}
