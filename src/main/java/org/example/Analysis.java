package org.example;

import java.io.File;
import java.util.List;

public class Analysis {
    public static void main(String[] args) throws Exception {
        Config c = new Config();
        // TODO: Add error handling for missing arguments
        List<Person> people = Parser.getAllLines(args[0], c.recordFormat, c.recordType);
        FreqTable table = new FreqTable(c.analysisType);
        table.addPeople(people);
        new File(c.resultsDir+"/tables").mkdirs();
        table.outputTable(c.resultsDir);
    }
}
