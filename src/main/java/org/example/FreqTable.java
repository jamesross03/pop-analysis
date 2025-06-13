package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.opencsv.CSVWriter;
import org.example.Person;

public class FreqTable {
    private final Map<String, Integer> counts;
    private final Function<Person, String> keyExtractor;
    private final String label;

    public FreqTable(String type) {
        counts = new HashMap<>();
        //TODO: REPLACE MAGIC STRINGS
        switch (type) {
            case Constants.FORENAME_FREQ -> {
                keyExtractor = FreqTable::extractForename;
                label = "forename";
            }
            case Constants.SURNAME_FREQ -> {
                keyExtractor = FreqTable::extractSurname;
                label = "surname";
            }
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    public void addPeople(List<Person> people) {
        for (Person p : people) {
            String key = keyExtractor.apply(p);
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }
    }

    public void outputTable(String results_dir) throws IOException {
        String output_fp = results_dir + "/tables/"+ label +"_freq.csv"; 
        try (CSVWriter writer = new CSVWriter(new FileWriter(output_fp))) {
            writer.writeNext(new String[]{capitalise(label), "Occurences"});
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                writer.writeNext(new String[]{entry.getKey(), entry.getValue().toString()});
            }
        }
    }

    private static String extractForename(Person p) {
        return p.getForename();
    }

    private static String extractSurname(Person p) {
        return p.getSurname();
    }

    private static String capitalise(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
