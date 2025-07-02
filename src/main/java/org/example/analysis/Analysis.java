package org.example.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.example.Config;

import com.github.jamesross03.pop_parser.RecordParser;
import com.github.jamesross03.pop_parser.utils.Record;
import com.opencsv.exceptions.CsvValidationException;

import uk.ac.standrews.cs.data.umea.io.PrintUmeaBirthRecordsSample;

/**
 * Main class for Pop-Analysis.
 */
public class Analysis {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("No config file given as CLI arg \nExpected usage: pop-analysis <config-filepath>");
            return;
        }
    
        try {
            String configFilepath = args[0];
            Config c = new Config(configFilepath);
            new File(c.getOutputDir()+"/tables").mkdirs();

            System.out.println("Running analysis with " + Paths.get(configFilepath).toAbsolutePath());

            new PrintUmeaBirthRecordsSample().run();
            // TODO: Uncomment this
            //runUmeaAnalysis(c, (Class<Record>)c.getRecordType());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Runs analysis with the provided Config object and Record Type, where T
     * is the Record type class (e.g BirthRecord).
     * 
     * @param config Config object to use
     * @param type Record type being read (e.g birth, marriage, death)
     * @throws CsvValidationException
     * @throws IOException
     */
    private static <T extends Record> void runUmeaAnalysis(Config config, Class<T> type) throws CsvValidationException, IOException {
        RecordParser<T> parser = new RecordParser<T>(type, config.getRecordFormat());
        List<T> records = parser.parse(config.getRecordsFilepath());

        FreqTable<T> table = new FreqTable(config);
        table.add(records);
        table.output();
    }
}
