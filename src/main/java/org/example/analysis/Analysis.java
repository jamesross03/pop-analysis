package org.example.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.example.Config;
import org.example.utils.Record;
import org.example.utils.parsers.RecordParser;

import com.opencsv.exceptions.CsvValidationException;

/**
 * Main class for Pop-Analysis.
 */
public class Analysis {
    public static void main(String[] args) throws Exception {
        try {
            if (args.length < 1) throw new IllegalArgumentException("No config file given as CLI arg");

            String configFilepath = args[0];
            Config c = new Config(configFilepath);
            new File(c.getOutputDir()+"/tables").mkdirs();

            System.out.println("Running analysis with " + Paths.get(configFilepath).toAbsolutePath());
            runAnalysis(c);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Runs analysis with the provided Config object.
     * 
     * @param config Config object to use
     * @throws CsvValidationException
     * @throws IOException
     */
    private static void runAnalysis(Config config) throws CsvValidationException, IOException {
        List<Record> records = RecordParser.getAllLines(config);

        FreqTable table = new FreqTable(config);
        table.add(records);
        table.output();
    }
}
