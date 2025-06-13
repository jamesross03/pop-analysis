package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.opencsv.CSVWriter;

/**
 * Generates a table of frequency values for a field in a record.
 */
public class FreqTable {
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
     * @param type defines target field
     */
    public FreqTable(String type) {
        counts = new HashMap<>();
        
        switch (type) {
            case Constants.FORENAME_FREQ -> {
                keyExtractor = Record::getForename;
                label = "forename";
            }
            case Constants.SURNAME_FREQ -> {
                keyExtractor = Record::getSurname;
                label = "surname";
            }
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    /**
     * Adds a List of records to the table.
     * 
     * @param records
     */
    public void add(List<Record> records) {
        for (Record r : records) {
            String key = keyExtractor.apply(r);
            // Increments count by 1 or default to 1 if no value yet
            counts.merge(key, 1, Integer::sum);
        }
    }

    /**
     * Outputs table to "<results_dir>/tables/<label>_freq.csv".
     * 
     * @param resultsDir
     * @throws IOException
     */
    public void output(String resultsDir) throws IOException {
        String filepath = resultsDir + "/tables/"+ label +"_freq.csv"; 

        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath))) {
            writer.writeNext(new String[]{Utils.capitalise(label), "Occurences"});
            
            counts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                    writer.writeNext(new String[]{entry.getKey(), entry.getValue().toString()});
                );
        }
    }
}
