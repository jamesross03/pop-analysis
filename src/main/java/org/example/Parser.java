package org.example;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    public static List<Map<String, String>> getAllLines(String fp) throws IOException, CsvValidationException {
        List<Map<String, String>> list = new ArrayList<>();
        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(fp))) {
            Map<String, String> values;
            while ((values = reader.readMap()) != null) {
                list.add(values);
            }
        }

        return list;
    }
}