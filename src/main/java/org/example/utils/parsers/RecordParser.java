package org.example.utils.parsers;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.example.utils.RecordFactory;
import org.example.utils.Record;

import uk.ac.standrews.cs.utilities.TimeManipulation;
import uk.ac.standrews.cs.utilities.archive.Diagnostic;

/**
 * Parser for record input files.
 */
public class RecordParser {
    // TODO: Consider an option of this which facilitates async execution (processing entries as they are received rather than in-order)
    /**
     * Reads all lines from an input file into objects.
     * 
     * @param filepath 
     * @param format record format
     * @param type record type
     * @return List of Records
     * @throws IOException
     * @throws CsvValidationException
     */
    public static List<Record> getAllLines(String filepath, String format, String type) throws IOException, CsvValidationException {
        final long START_TIME = System.currentTimeMillis();
        List<Record> list = new ArrayList<>();
        RecordFactory rf = new RecordFactory(format, type);

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(filepath))) {
            Map<String, String> values;
            Diagnostic.traceNoSource("Reading records from " + Paths.get(filepath));
            
            while ((values = reader.readMap()) != null) {
                Record r =  rf.makeRecord(values);
                if (r != null) list.add(r);
            }

            TimeManipulation.reportElapsedTime(START_TIME);
        }

        return list;
    }
}