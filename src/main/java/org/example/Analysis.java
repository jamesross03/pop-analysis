package org.example;

import java.io.File;
import java.util.List;

/**
 * Main class for Pop-Analysis.
 */
public class Analysis {
    public static void main(String[] args) throws Exception {
        Config c = new Config();
        new File(c.resultsDir+"/tables").mkdirs();

        // TODO: Add error handling for missing arguments
        List<Record> records = Parser.getAllLines(args[0], c.recordFormat, c.recordType);

        FreqTable table = new FreqTable(c.analysisType);
        table.add(records);
        table.output(c.resultsDir);
    }
}
