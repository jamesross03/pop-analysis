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
                keyExtractor = Record::getForenameStatic;
                label = "forename";
            }
            case Constants.SURNAME_FREQ -> {
                keyExtractor = Record::getSurnameStatic;
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
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }
    }

    /**
     * Outputs table to "<results_dir>/tables/<label>_freq.csv".
     * 
     * @param results_dir
     * @throws IOException
     */
    public void output(String results_dir) throws IOException {
        String output_fp = results_dir + "/tables/"+ label +"_freq.csv"; 
        try (CSVWriter writer = new CSVWriter(new FileWriter(output_fp))) {
            writer.writeNext(new String[]{Utils.capitalise(label), "Occurences"});
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                writer.writeNext(new String[]{entry.getKey(), entry.getValue().toString()});
            }
        }
    }
}
