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

    public FreqTable(String type) {
        counts = new HashMap<>();
        //TODO: REPLACE MAGIC STRINGS
        keyExtractor = switch (type) {
            case "FORENAME_FREQ" -> FreqTable::getForename;
            case "SURNAME_FREQ" -> FreqTable::getSurname;
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    
        if (keyExtractor == null) System.out.println("Here");
    }

    public void addPeople(List<Person> people) {
        for (Person p : people) {
            String key = keyExtractor.apply(p);
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }
    }

    public void outputTable(String output_fp) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(output_fp))) {
            writer.writeNext(new String[]{"Value", "Count"});

            // Data rows
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                writer.writeNext(new String[]{entry.getKey(), entry.getValue().toString()});
            }
        }
    }

    private static String getForename(Person p) {
        return p.getForename();
    }

    private static String getSurname(Person p) {
        return p.getSurname();
    }
}
