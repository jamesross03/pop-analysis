package org.example;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main class for Pop-Analysis.
 */
public class Analysis {
    public static void main(String[] args) throws Exception {
        try {
            Config c = new Config(args[0]);
            new File(c.getOutputDir()+"/tables").mkdirs();

            System.out.println("Running analysis with " + Paths.get(args[0]).toAbsolutePath());

            // TODO: Add error handling for missing arguments

            List<Record> records = Parser.getAllLines(c.getRecordsFilepath(), c.getRecordFormat(), c.getRecordType());

            FreqTable table = new FreqTable(c.getAnalysisType());
            table.add(records);
            table.output(c.getOutputDir());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
