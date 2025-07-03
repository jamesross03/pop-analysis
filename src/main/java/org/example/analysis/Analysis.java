package org.example.analysis;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.example.Config;
import org.example.utils.InfoFileWriter;

import com.github.jamesross03.pop_parser.RecordParser;
import com.github.jamesross03.pop_parser.utils.Record;
import com.opencsv.exceptions.CsvValidationException;

import uk.ac.standrews.cs.data.umea.UmeaBirthsDataSet;
import uk.ac.standrews.cs.utilities.dataset.DataSet;

/**
 * Main class for Pop-Analysis.
 */
public class Analysis {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();

        if (args.length < 1) {
            System.out.println("No config file given as CLI arg \nExpected usage: pop-analysis-umea <config-filepath>");
            return;
        }
    
        try {
            String configFilepath = args[0];
            Config c = new Config(configFilepath);
            new File(c.getOutputDir()+"/tables").mkdirs();

            System.out.println("Running analysis with " + Paths.get(configFilepath).toAbsolutePath());
            int n = runUmeaAnalysis(c, (Class<Record>)c.getRecordType());
            LocalDateTime endTime = LocalDateTime.now();
            InfoFileWriter.write(c, startTime, endTime, n);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Runs analysis with the provided Config object and Record Type, where T
     * is the Record type class (e.g BirthRecord), and record file input is
     * overridden to point to UMEA jar.
     * 
     * @param config Config object to use
     * @param type Record type being read (e.g birth, marriage, death)
     * @return number of records processed
     * @throws CsvValidationException
     * @throws IOException
     */
    private static <T extends Record> int runUmeaAnalysis(Config config, Class<T> type) throws CsvValidationException, IOException {
        RecordParser<T> parser = new RecordParser<T>(type, config.getRecordFormat());
        StringWriter writer = new StringWriter();
        
        DataSet dataset = switch(type.getSimpleName()) {
            case "BirthRecord" -> new UmeaBirthsDataSet();
            default -> throw new IllegalArgumentException("Unsupported record type: " + type);
        };

        dataset.print(writer); // writes CSV with headers and rows
        Stream<String> csvStream = writer.toString().lines();
        
        List<T> records = parser.parse(csvStream);

        FreqTable<T> table = new FreqTable(config);
        table.add(records);
        table.output();

        return records.size();
    }
}
