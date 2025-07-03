package org.example.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.example.Config;
import org.example.utils.InfoFileWriter;

import com.github.jamesross03.pop_parser.RecordParser;
import com.github.jamesross03.pop_parser.utils.Record;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Main class for Pop-Analysis.
 */
public class Analysis {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();

        if (args.length < 1) {
            System.out.println("No config file given as CLI arg \nExpected usage: pop-analysis <config-filepath>");
            return;
        }
    
        try {
            String configFilepath = args[0];
            Config c = new Config(configFilepath);
            new File(c.getOutputDir()+"/tables").mkdirs();

            System.out.println("Running analysis with " + Paths.get(configFilepath).toAbsolutePath());
            int n = runAnalysis(c, (Class<Record>)c.getRecordType());

            LocalDateTime endTime = LocalDateTime.now();
            InfoFileWriter.write(c, startTime, endTime, n);
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
     * @return number of records processed
     * @throws CsvValidationException
     * @throws IOException
     */
    private static <T extends Record> int runAnalysis(Config config, Class<T> type) throws CsvValidationException, IOException {
        RecordParser<T> parser = new RecordParser<T>(type, config.getRecordFormat());
        List<T> records = parser.parse(config.getRecordsFilepath());

        FreqTable<T> table = new FreqTable(config);
        table.add(records);
        table.output();

        return records.size();
    }
}
