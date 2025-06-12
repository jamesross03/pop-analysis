package org.example;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
    // TODO: Consider an option of this which facilitates async execution (processing entries as they are received rather than in-order)
    public static List<Person> getAllLines(String records_fp, String format, String type) throws IOException, CsvValidationException {
        List<Person> list = new ArrayList<>();
        PersonFactory rf = new PersonFactory(format, type);

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(records_fp))) {
            Map<String, String> values;
            while ((values = reader.readMap()) != null) {
                list.add(rf.makePerson(values));
            }
        }

        return list;
    }
}